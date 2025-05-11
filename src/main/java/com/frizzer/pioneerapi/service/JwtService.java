package com.frizzer.pioneerapi.service;

import com.frizzer.pioneerapi.domain.entity.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtService {

    private static final String USER_ID = "USER_ID";

    @Value("${jwt.secret-key}")
    private String secretKey;

    @Getter
    @Value("${jwt.expiration-time}")
    private long expirationTime;

    public String generateToken(Customer customer) {
        return Jwts.builder()
                   .subject(String.valueOf(customer.getId()))
                   .claims()
                   .add(USER_ID, customer.getId())
                   .and()
                   .issuedAt(new Date(System.currentTimeMillis()))
                   .expiration(new Date(System.currentTimeMillis() + expirationTime))
                   .signWith(getKey())
                   .compact();
    }

    public String extractUsername(String token) {
        return extractAll(token).getSubject();
    }

    public Claims extractAll(String token) {
        return Jwts.parser().verifyWith(getKey()).build().parseSignedClaims(token).getPayload();
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String name = extractUsername(token);
        return name.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractAll(token).getExpiration();
    }

    private SecretKey getKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}

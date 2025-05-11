package com.frizzer.pioneerapi.service;

import com.frizzer.pioneerapi.dao.EmailRepository;
import com.frizzer.pioneerapi.domain.dto.CustomerLoginDto;
import com.frizzer.pioneerapi.domain.entity.Customer;
import com.frizzer.pioneerapi.domain.entity.EmailData;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final EmailRepository emailRepository;

    public AuthService(AuthenticationManager authenticationManager, EmailRepository emailRepository) {
        this.authenticationManager = authenticationManager;
        this.emailRepository = emailRepository;
    }

    public Customer auth(CustomerLoginDto dto) {
        Customer customer = emailRepository.findByEmail(dto.getEmail())
                                           .map(EmailData::getCustomer)
                                           .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                   "Пользователь с такими данными не найден"));
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(customer.getUsername(),
                customer.getPassword()));
        return customer;
    }

    public static Customer getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Customer) authentication.getPrincipal();
    }

}

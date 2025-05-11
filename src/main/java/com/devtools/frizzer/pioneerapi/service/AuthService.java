package com.devtools.frizzer.pioneerapi.service;

import com.devtools.frizzer.pioneerapi.dao.EmailRepository;
import com.devtools.frizzer.pioneerapi.domain.dto.CustomerLoginDto;
import com.devtools.frizzer.pioneerapi.domain.entity.Customer;
import com.devtools.frizzer.pioneerapi.domain.entity.EmailData;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

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
                                           .orElseThrow();
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(customer.getUsername(), customer.getPassword()));
        return customer;
    }

}

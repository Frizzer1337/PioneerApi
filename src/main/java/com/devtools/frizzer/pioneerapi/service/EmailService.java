package com.devtools.frizzer.pioneerapi.service;

import com.devtools.frizzer.pioneerapi.dao.EmailRepository;
import com.devtools.frizzer.pioneerapi.domain.entity.EmailData;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    private final EmailRepository repository;

    public EmailService(EmailRepository repository) {this.repository = repository;}

    public EmailData save(EmailData data) {
        return repository.save(data);
    }
}

package com.frizzer.pioneerapi.service;

import com.frizzer.pioneerapi.dao.EmailRepository;
import com.frizzer.pioneerapi.domain.dto.EmailUpdateDto;
import com.frizzer.pioneerapi.domain.entity.Customer;
import com.frizzer.pioneerapi.domain.entity.EmailData;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EmailService {

    private final EmailRepository repository;

    public EmailService(EmailRepository repository) {this.repository = repository;}

    public void save(String email, long customerId) {
        if (repository.existsByEmail(email)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email уже занят");
        }
        EmailData emailData = new EmailData().withEmail(email).withCustomer(new Customer().withId(customerId));
        repository.save(emailData);
    }

    public void update(EmailUpdateDto dto, long customerId) {
        EmailData oldEmail = repository.findByEmailAndCustomer_Id(dto.getOldEmail(), customerId)
                                       .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                               "Email не найден"));
        if (repository.existsByEmail(dto.getNewEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email уже занят");
        }
        EmailData emailData = new EmailData().withId(oldEmail.getId())
                                             .withEmail(dto.getNewEmail())
                                             .withCustomer(new Customer().withId(customerId));
        repository.save(emailData);
    }

    @Transactional
    public void delete(String email, long customerId) {
        EmailData data = repository.findByEmailAndCustomer_Id(email,customerId)
                                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                           "Email не найден"));
        if(data.getCustomer().getEmailData().size() == 1){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Нельзя удалить единственный Email");
        }
        repository.delete(data);
    }
}

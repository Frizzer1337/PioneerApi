package com.frizzer.pioneerapi.service.cache;

import com.frizzer.pioneerapi.domain.dto.EmailUpdateDto;
import com.frizzer.pioneerapi.service.EmailService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class EmailCachedService {

    private final EmailService emailService;

    public EmailCachedService(EmailService emailService) {this.emailService = emailService;}

    @CacheEvict(value = "customers",allEntries = true)
    public void save(String email, long customerId) {
        emailService.save(email, customerId);
    }

    @CacheEvict(value = "customers",allEntries = true)
    public void update(EmailUpdateDto dto, long customerId) {
        emailService.update(dto, customerId);
    }

    @CacheEvict(value = "customers",allEntries = true)
    public void delete(String email, long customerId) {
        emailService.delete(email, customerId);
    }

}

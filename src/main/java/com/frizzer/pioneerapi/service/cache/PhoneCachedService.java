package com.frizzer.pioneerapi.service.cache;

import com.frizzer.pioneerapi.domain.dto.PhoneUpdateDto;
import com.frizzer.pioneerapi.service.PhoneService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class PhoneCachedService {

    private final PhoneService phoneService;

    public PhoneCachedService(PhoneService phoneService) {this.phoneService = phoneService;}

    @CacheEvict(value = "customers",allEntries = true)
    public void save(String phone, long customerId) {
        phoneService.save(phone, customerId);
    }

    @CacheEvict(value = "customers",allEntries = true)
    public void update(PhoneUpdateDto dto, long customerId) {
        phoneService.update(dto, customerId);
    }

    @CacheEvict(value = "customers",allEntries = true)
    public void delete(String email, long customerId) {
        phoneService.delete(email, customerId);
    }
}


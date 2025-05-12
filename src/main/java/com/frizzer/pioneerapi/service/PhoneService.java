package com.frizzer.pioneerapi.service;

import com.frizzer.pioneerapi.dao.PhoneRepository;
import com.frizzer.pioneerapi.domain.dto.PhoneUpdateDto;
import com.frizzer.pioneerapi.domain.entity.Customer;
import com.frizzer.pioneerapi.domain.entity.PhoneData;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
public class PhoneService {

    private final PhoneRepository repository;

    public PhoneService(PhoneRepository repository) {this.repository = repository;}

    @Transactional
    public void save(String phone, long customerId) {
        if (repository.existsByPhoneNumber(phone)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Номер уже занят");
        }
        PhoneData phoneData = new PhoneData().withPhoneNumber(phone).withCustomer(new Customer().withId(customerId));
        repository.save(phoneData);
    }

    @Transactional
    public void update(PhoneUpdateDto dto, long customerId) {
        PhoneData oldPhone = repository.findByPhoneNumberAndCustomer_Id(dto.getOldPhone(), customerId)
                                       .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                               "Номер не найден"));
        if (repository.existsByPhoneNumber(dto.getNewPhone())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Номер уже занят");
        }
        PhoneData phoneData = new PhoneData().withId(oldPhone.getId())
                                             .withPhoneNumber(dto.getNewPhone())
                                             .withCustomer(new Customer().withId(customerId));
        repository.save(phoneData);
    }

    @Transactional
    public void delete(String email, long customerId) {
        PhoneData data = repository.findByPhoneNumberAndCustomer_Id(email, customerId)
                                   .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                           "Номер не найден"));
        if (data.getCustomer().getPhoneData().size() == 1) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Нельзя удалить единственный номер");
        }
        repository.delete(data);
    }

}

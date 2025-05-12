package com.frizzer.pioneerapi.service.cache;

import com.frizzer.pioneerapi.domain.dto.CustomerDto;
import com.frizzer.pioneerapi.service.CustomerService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class CustomerCachedService {

    private final CustomerService customerService;

    public CustomerCachedService(CustomerService customerService) {this.customerService = customerService;}

    @Cacheable(value = "customers",
               key = "T(String).format('b=%s;n=%s;p=%s;e=%s;pg=%d;sz=%d', #begin, #name, #phone, #email, #pageable.pageNumber, #pageable.pageSize)")
    public List<CustomerDto> filter(LocalDate begin, String name, String phone, String email, Pageable pageable) {
        return customerService.filter(begin, name, phone, email, pageable);
    }

}

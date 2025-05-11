package com.frizzer.pioneerapi.service;

import com.frizzer.pioneerapi.dao.CustomerRepository;
import com.frizzer.pioneerapi.domain.dto.CustomerDto;
import com.frizzer.pioneerapi.domain.entity.Customer;
import com.frizzer.pioneerapi.domain.mapper.CustomerMapper;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.frizzer.pioneerapi.service.specification.CustomerSpecs.emailEquals;
import static com.frizzer.pioneerapi.service.specification.CustomerSpecs.nameLike;
import static com.frizzer.pioneerapi.service.specification.CustomerSpecs.phoneEquals;
import static com.frizzer.pioneerapi.service.specification.CustomerSpecs.wasBornAfter;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional(readOnly = true)
    public List<CustomerDto> filter(LocalDate begin, String name, String phone, String email, Pageable pageable) {
        Specification<Customer> filter = wasBornAfter(begin).and(nameLike(name))
                                                            .and(phoneEquals(phone))
                                                            .and(emailEquals(email));
        return customerRepository.findAll(filter, pageable)
                                 .stream()
                                 .map(customerMapper::toDto)
                                 .toList();
    }

}

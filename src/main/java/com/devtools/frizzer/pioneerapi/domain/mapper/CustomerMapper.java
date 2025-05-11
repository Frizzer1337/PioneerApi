package com.devtools.frizzer.pioneerapi.domain.mapper;

import com.devtools.frizzer.pioneerapi.domain.dto.CustomerDto;
import com.devtools.frizzer.pioneerapi.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

    CustomerDto toDto(Customer customer);

}
package com.frizzer.pioneerapi.domain.mapper;

import com.frizzer.pioneerapi.domain.dto.CustomerDto;
import com.frizzer.pioneerapi.domain.entity.Customer;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {EmailMapper.class,PhoneMapper.class})
public interface CustomerMapper {

    CustomerDto toDto(Customer customer);

}
package com.frizzer.pioneerapi.domain.mapper;

import com.frizzer.pioneerapi.domain.dto.CustomerDto;
import com.frizzer.pioneerapi.domain.entity.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {EmailMapper.class, PhoneMapper.class})
public interface CustomerMapper {

    @Mapping(source = "phoneData", target = "phones")
    @Mapping(source = "emailData", target = "emails")
    CustomerDto toDto(Customer customer);

}
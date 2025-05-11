package com.frizzer.pioneerapi.domain.mapper;

import com.frizzer.pioneerapi.domain.dto.PhoneDto;
import com.frizzer.pioneerapi.domain.entity.PhoneData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PhoneMapper {

    @Mapping(source = "phoneNumber", target = "phone")
    PhoneDto toDto(PhoneData phone);

    List<PhoneDto> toDto(List<PhoneData> phones);
}


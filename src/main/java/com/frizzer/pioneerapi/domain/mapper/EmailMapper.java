package com.frizzer.pioneerapi.domain.mapper;

import com.frizzer.pioneerapi.domain.dto.EmailDto;
import com.frizzer.pioneerapi.domain.entity.EmailData;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EmailMapper {

    EmailDto toDto(EmailData email);

    List<EmailDto> toDto(List<EmailData> emails);
}


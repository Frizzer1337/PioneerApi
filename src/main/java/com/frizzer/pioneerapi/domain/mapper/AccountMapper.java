package com.frizzer.pioneerapi.domain.mapper;

import com.frizzer.pioneerapi.domain.dto.AccountDto;
import com.frizzer.pioneerapi.domain.entity.Account;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AccountMapper {
    AccountDto toDto(Account account);
}

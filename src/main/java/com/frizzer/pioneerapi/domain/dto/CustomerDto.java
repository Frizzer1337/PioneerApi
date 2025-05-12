package com.frizzer.pioneerapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class CustomerDto {
    private long id;
    private String name;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yyyy")
    private LocalDate dateOfBirth;
    private String password;
    private List<PhoneDto> phones;
    private List<EmailDto> emails;
    private AccountDto account;
}

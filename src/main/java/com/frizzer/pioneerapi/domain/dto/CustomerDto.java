package com.frizzer.pioneerapi.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.frizzer.pioneerapi.domain.entity.Account;
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
    @JsonManagedReference
    private List<PhoneDto> phones;
    @JsonManagedReference
    private List<EmailDto> emails;
    @JsonManagedReference
    private Account account;
}

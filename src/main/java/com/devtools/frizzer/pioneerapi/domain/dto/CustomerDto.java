package com.devtools.frizzer.pioneerapi.domain.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerDto {
    private long id;
    private String username;
    private LocalDate dateOfBirth;
    private String password;
}

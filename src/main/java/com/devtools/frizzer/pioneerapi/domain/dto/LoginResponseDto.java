package com.devtools.frizzer.pioneerapi.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponseDto {
    private String token;
    private long expires;
}


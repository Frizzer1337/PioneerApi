package com.frizzer.pioneerapi.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {
    @Schema(description = "Баланс пользователя в формате рубли + копейки", example = "12.34")
    @PositiveOrZero(message = "Баланс не может быть меньше 0")
    private BigDecimal balance;
}

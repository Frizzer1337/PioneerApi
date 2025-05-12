package com.frizzer.pioneerapi.domain.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class TransferDto {

    @NotNull(message = "ID отправителя не может быть null")
    @PositiveOrZero(message = "ID должен быть больше либо равен 0")
    private Long id;

    @NotNull(message = "Сумма перевода не может быть null")
    @Positive(message = "Сумма перевода должна быть положительной")
    private BigDecimal amount;
}

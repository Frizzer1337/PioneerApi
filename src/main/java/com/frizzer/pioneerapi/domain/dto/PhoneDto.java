package com.frizzer.pioneerapi.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PhoneDto {
    @Schema(description = "Номер пользователя", example = "7XXXXXXXXXXXX")
    @Pattern(regexp = "^7\\d{12}$",
             message = "Номер пользователя должен быть 13 символов в длину, иметь формат 7XXXXXXXXXXXX")
    @NotNull(message = "Номер не должен быть пустым")
    private String phone;
}

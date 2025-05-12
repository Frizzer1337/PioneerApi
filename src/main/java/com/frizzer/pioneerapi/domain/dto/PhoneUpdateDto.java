package com.frizzer.pioneerapi.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class PhoneUpdateDto {
    @Schema(description = "Телефон пользователя", example = "7XXXXXXXXXXXX")
    @Pattern(regexp = "^7\\d{12}$",
             message = "Телефон пользователя должен быть 13 символов в длину, иметь формат 7XXXXXXXXXXXX")
    @NotNull
    private String oldPhone;
    @Schema(description = "Телефон пользователя", example = "7XXXXXXXXXXXX")
    @Pattern(regexp = "^7\\d{12}$",
             message = "Телефон пользователя должен быть 13 символов в длину, иметь формат 7XXXXXXXXXXXX")
    @NotNull
    private String newPhone;
}

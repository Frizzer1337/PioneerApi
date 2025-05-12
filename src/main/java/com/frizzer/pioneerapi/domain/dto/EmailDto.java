package com.frizzer.pioneerapi.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmailDto {
    @Schema(description = "Почта пользователя", example = "frizzer@gmail.com")
    @Size(min = 5, max = 200, message = "Почта должна быть от 5 до 200 символов")
    @Email(message = "Почта должна быть в формате frizzer@gmail.com")
    @NotNull(message = "Email не должен быть пустым")
    private String email;
}

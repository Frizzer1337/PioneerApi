package com.devtools.frizzer.pioneerapi.domain.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CustomerLoginDto {

    @Schema(description = "Почта пользователя", example = "frizzer@gmail.com")
    @Size(min = 5, max = 200, message = "Почта должна быть от 8 до 200 символов")
    @Email(message = "Почта в формате frizzer@gmail.com")
    private String email;

    @Schema(description = "Пароль", example = "My_STRONG_pass")
    @Size(min = 8, max = 500, message = "Длина пароля должна быть от 8 до 500 символов")
    private String password;

}

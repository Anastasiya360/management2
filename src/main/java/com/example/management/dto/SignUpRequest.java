package com.example.management.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(description = "Запрос на регистрацию")
public class SignUpRequest {

    @Schema(description = "Имя пользователя")
    @NotBlank(message = "Имя не может быть пустыми")
    private String name;

    @Schema(description = "Права пользователя(роль)")
    @NotBlank(message = "Обязательно к заполнению: user или admin")
    private String role;

    @Schema(description = "Фамилия пользователя")
    private String surname;

    @Schema(description = "Адрес электронной почты")
    @Size(min = 5, max = 255, message = "Адрес электронной почты должен содержать от 5 до 255 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустыми")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    private String email;

    @Schema(description = "Пароль")
    @Size(min = 8, max = 255, message = "Длина пароля должна быть не более 255 символов")
    private String password;
}

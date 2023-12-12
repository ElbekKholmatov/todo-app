package com.example.todoapp.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record ForgotPassDTO(
        @NotBlank(message = "Email must not be blank")
        @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Email example: example@mail.com")
        @Schema(description = "Email address", example = "example@mail.com")
        String email,

        @NotBlank(message = "verification code must not be blank")
        @Schema(description = "verification code", example = "11111")
        String code,
        @NotBlank(message = "Password must not be blank")
        @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
        @Schema(description = "New password for user", example = "password")
        String password,
        @NotBlank(message = "Confirm password must not be blank")
        @Size(min = 8, max = 50, message = "Confirm password must be between 8 and 50 characters")
        @Schema(description = "confirm new password for user", example = "password")
        String confirm_password
) {

}

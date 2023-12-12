package com.example.todoapp.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UserActivationResendDTO(
        @NotBlank(message = "Email must not be blank")
        @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Email example: example@mail.com")
        @Schema(description = "Email address", example = "example@mail.com")
        String email) {
}

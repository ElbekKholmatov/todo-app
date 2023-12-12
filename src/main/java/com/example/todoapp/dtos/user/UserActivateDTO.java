package com.example.todoapp.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import com.example.todoapp.dtos.error.BaseDTO;

public record UserActivateDTO(@NotBlank(message = "Activation code must not be blank")
                              @Pattern(regexp = "[0-9]{6}", message = "Activation code must be 6 characters long example: 123456")
                              @Schema(description = "Activation code", example = "")
                              String code,
                              @NotBlank(message = "Email must not be blank")
                              @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Email example: example@mail.com")
                              @Schema(description = "Email address", example = "example@mail.com")
                              String email) implements BaseDTO {
}
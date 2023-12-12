package com.example.todoapp.dtos.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import com.example.todoapp.dtos.error.BaseDTO;

public record CodeForgotPassword(
        @NotBlank(message = "Phone number must not be blank")
        @Pattern(regexp = "(\\+[0-9]{12})", message = "Phone number must be 13 characters long example: +998901234567")
        @Schema(description = "Phone number", example = "+998999999999")
        String phone
) implements BaseDTO {
}

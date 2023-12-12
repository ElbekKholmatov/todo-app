package com.example.todoapp.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ResetPassDTO(
        @NotBlank(message = "Enter old password")
        @Schema(description = " Old password of the user")
        String old_password,
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

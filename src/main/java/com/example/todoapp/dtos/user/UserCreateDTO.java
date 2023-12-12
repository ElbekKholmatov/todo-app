package com.example.todoapp.dtos.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import com.example.todoapp.dtos.CreateDTO;

import java.time.LocalDate;
import java.util.Objects;

public record UserCreateDTO(@NotBlank(message = "Email must not be blank")
                            @Pattern(regexp = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$", message = "Email example: example@mail.com")
                            @Schema(description = "Email address", example = "example@mail.com")
                            String email,
                            @NotBlank(message = "Password must not be blank")
                            @Size(min = 8, max = 50, message = "Password must be between 8 and 50 characters")
                            @Schema(description = "Password of the user", example = "password")
                            String password,
                            @NotBlank(message = "Confirm password must not be blank")
                            @Size(min = 8, max = 50, message = "Confirm password must be between 8 and 50 characters")
                            @Schema(description = "Confirm password of the user", example = "password")
                            String confirmPassword

) implements CreateDTO {

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (UserCreateDTO) obj;
        return Objects.equals(this.password, that.password) &&
                Objects.equals(this.confirmPassword, that.confirmPassword);
    }


}




package com.example.todoapp.validation;


import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import com.example.todoapp.domain.user.User;
import com.example.todoapp.dtos.user.UserCreateDTO;
import com.example.todoapp.enums.UserStatus;
import com.example.todoapp.exceptions.DuplicateEntryFoundException;
import com.example.todoapp.repositories.user.UserRepository;

@Service
@RequiredArgsConstructor
public class UserValidator {
    private final UserRepository userRepository;
    private final OTPValidator otpValidator;

    public void validate(@NotNull UserCreateDTO dto) {
        if (!dto.password().trim().equals(dto.confirmPassword().trim()))
            throw new IllegalArgumentException("Passwords does not match!");
        existsUserByUsername(dto.email());
    }


    public void validate(String code, @NotNull User user) {
        if (user.getStatus().equals(UserStatus.ACTIVE))
            throw new IllegalArgumentException("User already activated!");
        otpValidator.validate(code, user.getId());
    }

    public void existsUserByUsername(String email) {
        if (userRepository.existsUserByUsername(email))
            throw new DuplicateEntryFoundException("User is already registered with the email: " + email);
    }

}

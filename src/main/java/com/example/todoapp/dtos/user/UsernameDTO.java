package com.example.todoapp.dtos.user;

public record UsernameDTO(
        String old_email,
        String new_email,
        String password,
        String otp
) {
}

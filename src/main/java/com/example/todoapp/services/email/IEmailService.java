package com.example.todoapp.services.email;

public interface IEmailService {
    void sendOtp(String toNumber, String otp);
}

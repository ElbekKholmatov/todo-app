package com.example.todoapp.services.auth;

import com.example.todoapp.services.email.IEmailService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import com.example.todoapp.domain.user.OTP;
import com.example.todoapp.domain.user.User;
import com.example.todoapp.repositories.auth.OTPRepository;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
public class OTPService {
    private final OTPRepository otpRepository;
    private final IEmailService emailService;
    private final Integer ACTIVATION_CODE_EXPIRY = 20;

    public void send(User user) {
        CompletableFuture.runAsync(() -> {
            String code = generateOTP();
            emailService.sendOtp(user.getUsername(), code);
            OTP otp = new OTP(code, user.getId(), LocalDateTime.now().plusMinutes(ACTIVATION_CODE_EXPIRY), OTP.OtpType.ACCOUNT_ACTIVATE);
            otpRepository.save(otp);

        });
    }

    private static @NotNull String generateOTP() {
        return String.valueOf(new SecureRandom().nextInt(100000, 999999));
    }
}
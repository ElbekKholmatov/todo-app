package com.example.todoapp.validation;

import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import com.example.todoapp.domain.user.OTP;
import com.example.todoapp.domain.user.User;
import com.example.todoapp.repositories.auth.OTPRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.example.todoapp.enums.UserStatus.ACTIVE;

@Service
@RequiredArgsConstructor
public class OTPValidator {

    private final OTPRepository otpRepository;

    public void validate(String code, Long userID) {

        OTP otp = otpRepository.existsByCodeAndUserID(code, userID)
                .orElseThrow(() -> new IllegalArgumentException("Incorrect or invalid OTP!"));
        if (LocalDateTime.now().isAfter(otp.getExpiresAt())) throw new RuntimeException("OTP expired!");
        if (!code.equals(otp.getCode())) throw new IllegalArgumentException("OTP is not valid!");
        otpRepository.delete(otp);
    }

    public void validate(@NotNull User user) {

        if (user.getStatus().equals(ACTIVE)) throw new IllegalArgumentException("User already activated!");

        Optional<OTP> mostRecentByUserIDAndOtpType = otpRepository
                .findMostRecentByUserIDAndOtpType(user.getId(), OTP.OtpType.ACCOUNT_ACTIVATE);

        if (mostRecentByUserIDAndOtpType.isPresent() && isEligibleForOTP(mostRecentByUserIDAndOtpType.get()))
            throw new IllegalArgumentException("OTP already sent!");

    }
    private boolean isEligibleForOTP(@NotNull OTP otp) {
        return otp.getCreatedAt().plusMinutes(2).isAfter(LocalDateTime.now());
    }
}
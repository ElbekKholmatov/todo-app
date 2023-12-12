package com.example.todoapp.domain.user;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import com.example.todoapp.domain.BaseEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class OTP implements BaseEntity {
    @Id
    @SequenceGenerator(name = "otpIdGenerator", sequenceName = "otp_id_generator", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "otpIdGenerator")
    private Long ID;

    @Column(unique = true, nullable = false)
    private String code;


    @Column(nullable = false)
    private Long userID;

    @Column(nullable = false)
    private LocalDateTime expiresAt;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OtpType otpType;

    @CreationTimestamp
    private LocalDateTime createdAt;

    public OTP(String code, Long userID, LocalDateTime expiresAt, OtpType otpType) {
        this.code = code;
        this.userID = userID;
        this.expiresAt = expiresAt;
        this.otpType = otpType;
    }

    public enum OtpType {
        PASSWORD_RESET, PHONE_NUMBER_CHANGE, FORGOT_PASSWORD, ACCOUNT_ACTIVATE
    }
}

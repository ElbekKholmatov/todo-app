package com.example.todoapp.repositories.auth;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import com.example.todoapp.domain.user.OTP;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    @Query("from OTP o where o.code = ?1 and o.userID = ?2")
    Optional<OTP> existsByCodeAndUserID(String code, Long userID);


    @Query("from OTP o where o.userID = ?1 and o.otpType = ?2 order by o.createdAt desc")
    Optional<OTP> findMostRecentByUserIDAndOtpType(Long id, OTP.OtpType otpType);

    @Modifying
    @Query("delete from OTP o where o.userID = ?1 and o.otpType = ?2")
    void deleteAllByUserIDAndOtpType(Long id, OTP.OtpType otpType);
}
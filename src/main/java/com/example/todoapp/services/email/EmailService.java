package com.example.todoapp.services.email;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class EmailService implements IEmailService {
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String username;
    @Override
    public void sendOtp(String toEmail, String otp) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(toEmail);
        message.setText("Your one time password is "+otp+" .");
        message.setSubject("ONE TIME PASSWORD");
        mailSender.send(message);
        System.out.println("Mail Send...");
    }
}

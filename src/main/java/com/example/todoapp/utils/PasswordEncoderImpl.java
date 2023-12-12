package com.example.todoapp.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoderImpl extends BCryptPasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return super.encode(rawPassword);
    }

}


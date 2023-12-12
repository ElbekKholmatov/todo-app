package com.example.todoapp.services.auth;


import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.todoapp.configuration.security.JWTUtil;
import com.example.todoapp.domain.user.OTP;
import com.example.todoapp.domain.user.User;
import com.example.todoapp.dtos.auth.*;
import com.example.todoapp.dtos.user.UserActivateDTO;
import com.example.todoapp.dtos.user.UserActivationResendDTO;
import com.example.todoapp.enums.TokenType;
import com.example.todoapp.repositories.auth.OTPRepository;
import com.example.todoapp.repositories.user.UserRepository;
import com.example.todoapp.services.user.UserService;
import com.example.todoapp.validation.OTPValidator;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtils;
    private final OTPValidator otpValidator;
    private final OTPService otpService;

    public TokenResponse generateToken(@NotNull TokenRequest tokenRequest) {
        String username = tokenRequest.email();
        String password = tokenRequest.password();
        userService.getUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authentication);
        return jwtUtils.generateToken(username);
    }

    public TokenResponse refreshToken(@NotNull RefreshTokenRequest refreshTokenRequest) {

        String refreshToken = refreshTokenRequest.refreshToken();
        if (!jwtUtils.isTokenValid(refreshToken, TokenType.REFRESH))
            throw new CredentialsExpiredException("Token is invalid");

        String username = jwtUtils.getUsername(refreshToken, TokenType.REFRESH);
        userService.getUserByUsername(username);
        TokenResponse tokenResponse = TokenResponse.builder()
                .refreshToken(refreshToken)
                .refreshTokenExpiry(jwtUtils.getExpiry(refreshToken, TokenType.REFRESH)).build();
        return jwtUtils.generateAccessToken(username, tokenResponse);
    }

    public String activate(UserActivateDTO dto) {
        userService.updateStatus(dto);
        return "User successfully activated with email: %s !".formatted(dto.email());
    }

    public String resendActivation(@NotNull UserActivationResendDTO dto) {
        User user = userService.getUserByUsername(dto.email());
        otpValidator.validate(user);
        otpService.send(user);
        return "Activation code successfully resent!";
    }

}

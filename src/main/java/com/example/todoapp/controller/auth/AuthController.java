package com.example.todoapp.controller.auth;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.todoapp.domain.user.User;
import com.example.todoapp.dtos.auth.*;
import com.example.todoapp.dtos.error.AppErrorDTO;
import com.example.todoapp.dtos.user.UserActivateDTO;
import com.example.todoapp.dtos.user.UserActivationResendDTO;
import com.example.todoapp.dtos.user.UserCreateDTO;
import com.example.todoapp.dtos.user.UserDTO;
import com.example.todoapp.services.auth.AuthService;
import com.example.todoapp.services.user.UserService;

@RestController
@RequestMapping({"/api/v1/auth"})
@RequiredArgsConstructor
@Tag(name = "Authorization", description = "User Registration, Activation; Access & Refresh Token generation")
public class AuthController {
    private final UserService userService;
    private final AuthService authService;


    @Operation(summary = "This API is used for access token generation", responses = {
            @ApiResponse(responseCode = "200", description = "Access token generated", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))},
            description = """
                            To generate token you have to provide EMAIL_ADDRESS and PASSWORD in request body.
                            When you enter both the EMAIL_ADDRESS and the PASSWORD correctly,
                            you will get the ACCESS TOKEN and the REFRESH TOKEN
                            The ACCESS TOKEN is valid for 60 minutes and the REFRESH TOKEN is VALID for 2 days.
                            You can use the REFRESH TOKEN to generate a new ACCESS TOKEN when the ACCESS TOKEN expires.
                    """)
    @PostMapping({"/access/token"})
    public ResponseEntity<TokenResponse> generateToken(@RequestBody @Valid TokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.generateToken(tokenRequest));
    }

    @Operation(summary = "This API is used for generating a new access token using the refresh token",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Access token generated", content = @Content(schema = @Schema(implementation = TokenResponse.class))),
                    @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))},
            description = """
                            You HAVE to provide ONLY the REFRESH TOKEN in the request body.
                            If you provide ACCESS token instead of a REFRESH TOKEN, you will get an error.
                            When you provide the REFRESH TOKEN, you will get a new ACCESS TOKEN and a new REFRESH TOKEN.
                            The new ACCESS TOKEN is VALID for 60 minutes and the new REFRESH TOKEN is VALID for 2 days.
                    """)
    @PostMapping({"/refresh/token"})
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody @Valid RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    @Operation(summary = "This API is used for user registration", responses = {
            @ApiResponse(responseCode = "200", description = "User registered successfully", content = @Content(schema = @Schema(implementation = User.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))},
            description = """
                          In the EMAIL_ADDRESS field, please provide a valid email address. If you provide a email address,
                          you will receive an activation code via email, and the provided email address will be used as the EMAIL_ADDRESS for further processing.
                    """)
    @PostMapping({"/register"})
    public ResponseEntity<UserDTO> register(@RequestBody @Valid UserCreateDTO dto) {
        return ResponseEntity.ok(userService.create(dto));
    }

    @Operation(summary = "This API is used for user activating users through the activation code that was sent via SMS", responses = {
            @ApiResponse(responseCode = "200", description = "User activated", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))},
            description = """
                            The OTP consists of 6 digits.
                            The activation code will EXPIRE in 10 MINUTES.
                    """)
    @PostMapping({"/activate"})
    public ResponseEntity<String> activate(@RequestBody @Valid UserActivateDTO dto) {
        return ResponseEntity.ok(authService.activate(dto));
    }

    @Operation(summary = "This API is used for resending the activation code to the user's phone number", responses = {
            @ApiResponse(responseCode = "200", description = "Activation code resent", content = @Content(schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "400", description = "Bad request", content = @Content(schema = @Schema(implementation = AppErrorDTO.class)))},
            description = """
                           Users allowed to request a new activation code EVERY 2 MINUTES.
                           An already activated user will get an error: 'User already activated!'
                    """)
    @PostMapping("/code/resend")
    public ResponseEntity<String> resendActivation(@RequestBody @Valid UserActivationResendDTO dto) {
        return ResponseEntity.ok(authService.resendActivation(dto));
    }
}

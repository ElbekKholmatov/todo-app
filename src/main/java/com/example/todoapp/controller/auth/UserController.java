package com.example.todoapp.controller.auth;

import com.example.todoapp.configuration.session.SessionUser;
import com.example.todoapp.dtos.auth.TokenResponse;
import com.example.todoapp.dtos.error.AppErrorDTO;
import com.example.todoapp.dtos.user.ForgotPassDTO;
import com.example.todoapp.dtos.user.ResetPassDTO;
import com.example.todoapp.dtos.user.UserDTO;
import com.example.todoapp.dtos.user.UsernameDTO;
import com.example.todoapp.services.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping({"/api/v1/user"})
@RequiredArgsConstructor
@Tag(name = "UserController", description = "User Current, Reset Password, Forgot Password, Change Email")
public class UserController {

    private final UserService userService;
    private final SessionUser sessionUser;

    @Operation(summary = "This API is used for get current user by token", responses = {
            @ApiResponse(responseCode = "200", description = "get User Info by JWT token", content = @Content(schema = @Schema(implementation = ResponseEntity.class)))},
            description = """
                            This endpoint is useful for fetching the currently logged-in user's information, such as username, email, role, etc.        
                    """)
    @GetMapping("/current")
    public ResponseEntity<UserDTO> getCurrentUser() {
        UserDTO user = userService.getUserData(sessionUser);
        return ResponseEntity.ok(user);
    }
    @Operation(
            summary = "Reset User Password",
            description = """
                            Reset the user's password with the provided data
                    """

    )
    @ApiResponse(
            responseCode = "200",
            description = """
                                    Password successfully changed
                            """,
            content = @Content(schema = @Schema(implementation = ResponseEntity.class))
    )
    @PostMapping("/reset_password")
    public ResponseEntity<String> resetPassword(@RequestBody @Valid ResetPassDTO dto) {
        userService.resetPassword(dto, sessionUser);
        return ResponseEntity.ok("Password successfully changed");
    }

}

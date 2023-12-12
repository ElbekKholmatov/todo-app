package com.example.todoapp.services.user;


import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import com.example.todoapp.configuration.session.SessionUser;
import com.example.todoapp.domain.user.User;
import com.example.todoapp.dtos.user.*;
import com.example.todoapp.enums.UserStatus;
import com.example.todoapp.exceptions.ItemNotFoundException;
import com.example.todoapp.mappers.UserMapper;
import com.example.todoapp.repositories.user.UserRepository;
import com.example.todoapp.utils.PasswordEncoderImpl;
import com.example.todoapp.services.auth.OTPService;
import com.example.todoapp.validation.OTPValidator;
import com.example.todoapp.validation.UserValidator;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final OTPService otpService;
    private final UserRepository userRepository;
    private final UserValidator userValidator;
    private final UserMapper userMapper;
    private final PasswordEncoderImpl passwordEncoder;
    private final OTPValidator otpValidator;

    public UserDTO create(UserCreateDTO dto) {
        userValidator.validate(dto);
        User user = userMapper.toEntity(dto);
        User savedUser = userRepository.save(user);
        otpService.send(user);
        return userMapper.toDTO(savedUser);
    }

    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new ItemNotFoundException("User not found with username: " + username));
    }


    public void updateStatus(@NotNull UserActivateDTO dto) {
        String username = dto.email();
        User user = getUserByUsername(username);
        userValidator.validate(dto.code(), user);
        user.setStatus(UserStatus.ACTIVE);
        userRepository.save(user);
    }

    public void resetPassword(@NotNull ResetPassDTO dto, @NotNull SessionUser sessionUser) {
        User user = sessionUser.user();
        isOldPasswordMatch(dto.old_password(), user.getPassword(), false, "Old password is incorrect");
        isPasswordEqual(dto, false, "Passwords do not match");
        isOldPasswordMatch(dto.old_password(), dto.password(), true, "Use new password. Password you entered was Used before");
        user.setPassword(passwordEncoder.encode(dto.password()));
        userRepository.save(user);
    }

    public void forgotPassword(@NotNull ForgotPassDTO dto) {
        User user = getUserByUsername(dto.email());
        otpValidator.validate(dto.code(), user.getId());
        if (!dto.password().equals(dto.confirm_password()))
            throw new RuntimeException("Passwords do not match");
        user.setPassword(passwordEncoder.encode(dto.password()));
        userRepository.save(user);
    }

    public void isPasswordEqual(@NotNull ResetPassDTO dto, boolean check, String message) {
        if (dto.password().equals(dto.confirm_password()) == check)
            throw new RuntimeException(message);
    }

    public void isOldPasswordMatch(String dtoPassword, String correctPassword, boolean check, String message) {
        if (passwordEncoder.matches(dtoPassword, correctPassword) == check) throw new RuntimeException(message);
    }

    public UserDTO getUserData(@NotNull SessionUser sessionUser) {
        return userMapper.toDTO(sessionUser.user());
    }

    public User findByID(Long id) {
        if (id == null || id==0 )
            throw new ItemNotFoundException("Id should be greater than 0");
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty())
            throw new ItemNotFoundException("User with id "+id+" not found.");
        return optionalUser.get();
    }
}

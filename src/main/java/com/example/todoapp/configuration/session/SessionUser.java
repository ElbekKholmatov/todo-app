package com.example.todoapp.configuration.session;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import com.example.todoapp.domain.user.User;
import com.example.todoapp.services.user.UserService;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class SessionUser {
    private final UserService userService;

    public User user() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();

        String name = authentication.getName();
        if (authentication.isAuthenticated() && !name.equals("anonymousUser")) {
            return userService.getUserByUsername(name);
        }
        return null;
    }

    public Long ID() {
        User user = user();
        if (Objects.isNull(user))
            return -1L;
        return user.getId();
    }
}

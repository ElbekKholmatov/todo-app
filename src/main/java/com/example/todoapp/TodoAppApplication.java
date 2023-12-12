package com.example.todoapp;

import com.example.todoapp.domain.todo.Todo;
import com.example.todoapp.domain.user.User;
import com.example.todoapp.enums.Priority;
import com.example.todoapp.enums.Status;
import com.example.todoapp.enums.UserRole;
import com.example.todoapp.enums.UserStatus;
import com.example.todoapp.repositories.todo.TodoRepository;
import com.example.todoapp.repositories.user.UserRepository;
import com.example.todoapp.utils.PasswordEncoderImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@SpringBootApplication
@EnableScheduling
public class TodoAppApplication {
    private final PasswordEncoderImpl passwordEncoder;

    public static void main(String[] args) {
        SpringApplication.run(TodoAppApplication.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner(UserRepository userRepository, TodoRepository todoRepository) {
        return args -> {
            for (int i = 0; i < 3; i++) {
                if (userRepository.findByUsername("example"+i+"@mail.com").isEmpty()) {
                    User user = new User("example"+i+"@mail.com", passwordEncoder.encode("password123"));
                    user.setStatus(UserStatus.ACTIVE);
                    user.setRole(UserRole.USER);
                    userRepository.save(user);
                }
            }
            for (int i = 0; i < 30; i++) {
                Optional<Todo> optionalTodo = todoRepository.findById((long) i);
                if (optionalTodo.isEmpty()) {
                    Todo entity = new Todo(
                            "title" + i,
                            "description" + i,
                            (i % 3 == 0) ? Status.TODO : ((i % 3 == 1) ? Status.WAITING : Status.DONE),
                            (i % 3 == 0) ? Priority.HIGH : ((i % 3 == 1) ? Priority.MEDIUM : Priority.LOW),
                            LocalDateTime.now(),
                            userRepository.findById(1L).get()
                    );
                    todoRepository.save(entity);
                }
            }
        };
    }
}

package com.example.todoapp.dtos.todo;

import com.example.todoapp.domain.user.User;
import com.example.todoapp.dtos.CreateDTO;
import com.example.todoapp.enums.Priority;
import com.example.todoapp.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.jetbrains.annotations.NotNull;

import com.example.todoapp.enums.Priority;
import com.example.todoapp.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.jetbrains.annotations.NotNull;

public record CreateTodoDTO(
        @NotBlank(message = "title must not be blank")
        @Schema(description = "Title of todo", example = "some text")
        String title,

        @NotBlank(message = "description must not be blank")
        @Schema(description = "description of todo", example = "some text")
        String description,
        @NotBlank(message = "status must not be blank")
        Status status,
        @NotBlank(message = "priority must not be blank")
        Priority priority,
        Long executorID
) implements CreateDTO {
}


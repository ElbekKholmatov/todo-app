package com.example.todoapp.dtos.todo;

import com.example.todoapp.dtos.DeleteDTO;

public record DeleteTodoDTO(
        Long todoID
) implements DeleteDTO {
}

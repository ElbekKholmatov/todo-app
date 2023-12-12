package com.example.todoapp.dtos.todo;

import com.example.todoapp.dtos.DTO;

public record TodoDTO(
        Long id,
        String title,
        String description,
        String status,
        String priority,
        Long creatorID,
        Long executorID
) implements DTO {
}

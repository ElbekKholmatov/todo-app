package com.example.todoapp.dtos.comment;

import com.example.todoapp.dtos.DTO;

public record CommentDTO(
        Long id,
        String comment,
        Long todoID,
        Long commentatorID
) implements DTO {
}

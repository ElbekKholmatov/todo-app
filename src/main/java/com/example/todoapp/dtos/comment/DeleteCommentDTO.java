package com.example.todoapp.dtos.comment;

import com.example.todoapp.dtos.DeleteDTO;

public record DeleteCommentDTO(
        Long todoID
) implements DeleteDTO {
}

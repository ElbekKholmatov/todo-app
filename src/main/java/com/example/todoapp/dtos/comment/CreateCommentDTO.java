package com.example.todoapp.dtos.comment;

import com.example.todoapp.dtos.CreateDTO;
import com.example.todoapp.enums.Priority;
import com.example.todoapp.enums.Status;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record CreateCommentDTO(
        @NotBlank(message = "todoID must not be blank")
        @Schema(description = "ID of todo", example = "1")
        Long todoID,

        @NotBlank(message = "comment must not be blank")
        @Schema(description = "comment to todo", example = "some text")
        String comment
) implements CreateDTO {
}


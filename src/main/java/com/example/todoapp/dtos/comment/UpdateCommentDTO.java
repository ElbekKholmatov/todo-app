package com.example.todoapp.dtos.comment;

import com.example.todoapp.dtos.UpdateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCommentDTO implements UpdateDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private Long executorID;
}


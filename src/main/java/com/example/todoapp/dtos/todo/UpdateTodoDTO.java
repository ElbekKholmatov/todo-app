package com.example.todoapp.dtos.todo;

import com.example.todoapp.dtos.UpdateDTO;
import com.example.todoapp.enums.Priority;
import com.example.todoapp.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateTodoDTO implements UpdateDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private String priority;
    private Long executorID;
}


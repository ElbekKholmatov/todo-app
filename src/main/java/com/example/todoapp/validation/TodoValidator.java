package com.example.todoapp.validation;

import com.example.todoapp.dtos.todo.CreateTodoDTO;
import com.example.todoapp.dtos.todo.UpdateTodoDTO;
import com.example.todoapp.enums.Priority;
import com.example.todoapp.enums.Status;
import com.example.todoapp.exceptions.ItemNotFoundException;
import com.example.todoapp.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.BadRequestException;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TodoValidator {
    private final UserService userService;

    public void validate(CreateTodoDTO dto) {

        if (dto.executorID()!=null) {
            userService.findByID(dto.executorID());
        }
    }

    public void validate(UpdateTodoDTO dto) {
        if(dto.getStatus()!=null){
            Status.valueOf(dto.getStatus());
        }
        if(dto.getPriority()!=null){
            Priority.valueOf(dto.getPriority());
        }
        if (dto.getExecutorID()!=null) {
            userService.findByID(dto.getExecutorID());
        }
    }
}

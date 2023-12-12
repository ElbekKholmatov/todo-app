package com.example.todoapp.services.todo;

import com.example.todoapp.domain.todo.Todo;
import com.example.todoapp.dtos.CreateDTO;
import com.example.todoapp.dtos.GetDTO;
import com.example.todoapp.dtos.UpdateDTO;
import com.example.todoapp.dtos.todo.*;
import com.example.todoapp.services.CRUDService;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface TodoService extends CRUDService<Todo, CreateTodoDTO, UpdateTodoDTO, DeleteTodoDTO, TodoDTO, GetTodoDTO> {
    Todo findByID(Long id);
}

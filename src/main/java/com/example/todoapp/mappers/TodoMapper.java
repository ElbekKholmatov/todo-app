package com.example.todoapp.mappers;

import com.example.todoapp.domain.todo.Todo;
import com.example.todoapp.dtos.todo.CreateTodoDTO;
import com.example.todoapp.dtos.todo.TodoDTO;
import com.example.todoapp.dtos.todo.UpdateTodoDTO;
import lombok.Builder;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface TodoMapper extends GenericMapper<CreateTodoDTO, UpdateTodoDTO, TodoDTO, Todo>{

    @Override
    default TodoDTO toDTO(Todo entity){
        return new TodoDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus().toString(),
                entity.getPriority().toString(),
                entity.getCreator().getId(),
                entity.getExecutor().getId()
        );
    }
}

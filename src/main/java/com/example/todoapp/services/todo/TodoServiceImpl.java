package com.example.todoapp.services.todo;

import com.example.todoapp.configuration.session.SessionUser;
import com.example.todoapp.domain.todo.Todo;
import com.example.todoapp.domain.user.User;
import com.example.todoapp.dtos.todo.*;
import com.example.todoapp.enums.Priority;
import com.example.todoapp.enums.Status;
import com.example.todoapp.exceptions.ItemNotFoundException;
import com.example.todoapp.mappers.TodoMapper;
import com.example.todoapp.repositories.todo.TodoRepository;
import com.example.todoapp.services.SpecificationService;
import com.example.todoapp.services.user.UserService;
import com.example.todoapp.validation.TodoValidator;
import jakarta.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final SessionUser sessionUser;
    private final TodoRepository todoRepository;
    private final UserService userService;
    private final TodoMapper todoMapper;
    private final TodoValidator todoValidator;
    private final SpecificationService specificationService;


    @Override
    public TodoDTO create(CreateTodoDTO dto) {
        User user = sessionUser.user();
        todoValidator.validate(dto);
        Todo todo = todoMapper.toEntity(dto);
        todo.setCreator(user);
        if (!Objects.isNull(dto.executorID())) {
            todo.setExecutor(userService.findByID(dto.executorID()));
        }
        todo.setCreatedAt(LocalDateTime.now());
        Todo save = todoRepository.save(todo);
        return todoMapper.toDTO(save);
    }

    @Override
    public TodoDTO findById(Long id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isEmpty()) {
            throw new ItemNotFoundException("todo with this id not found");
        }
        Todo todo = optionalTodo.get();
        return todoMapper.toDTO(todo);
    }
    @Override
    public Todo findByID(Long id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isEmpty()) {
            throw new ItemNotFoundException("todo with this id not found");
        }
        return optionalTodo.get();
    }
    public Todo findTodoByIdAndCreatorId(Long todoID, Long creatorID) {
        Optional<Todo> optionalTodo = todoRepository.findByIdAndCreatorId(todoID,creatorID);
        if (optionalTodo.isEmpty()) {
            throw new ItemNotFoundException("todo not found");
        }
        return optionalTodo.get();
    }

    @Override
    public Collection<TodoDTO> findAll() {
        return todoRepository.findAll().stream().map(todoMapper::toDTO).toList();
    }

    public Page<TodoDTO> findAll(GetTodoDTO dto) {
        Sort sort = Sort.by("createdAt").descending();
        PageRequest pageable = PageRequest.of(dto.getPage(), dto.getSize(),sort);
        Specification<Todo> specification = specificationService.getSpecification(dto);
        return todoRepository.findAllBySpecification(specification, pageable).map(todoMapper::toDTO);
    }

    @Override
    public TodoDTO update(UpdateTodoDTO dto) {
        Todo beforeUpdate = findTodoByIdAndCreatorId(dto.getId(), sessionUser.ID());
        todoValidator.validate(dto);
        Todo afterUpdate = todoMapper.partialUpdate(dto,beforeUpdate);
        if (dto.getExecutorID()!=null){
            User newExecutor = userService.findByID(dto.getExecutorID());
            afterUpdate.setExecutor(newExecutor);
        }
        return todoMapper.toDTO(todoRepository.save(afterUpdate));
    }

    @Override
    public void delete(DeleteTodoDTO dto) {
        Todo todo = findTodoByIdAndCreatorId(dto.todoID(), sessionUser.ID());
        todoRepository.delete(todo);
    }


}

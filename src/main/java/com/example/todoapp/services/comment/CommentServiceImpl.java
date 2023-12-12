package com.example.todoapp.services.comment;

import com.example.todoapp.configuration.session.SessionUser;
import com.example.todoapp.domain.comment.Comment;
import com.example.todoapp.domain.todo.Todo;
import com.example.todoapp.domain.user.User;
import com.example.todoapp.dtos.comment.*;
import com.example.todoapp.mappers.CommentMapper;
import com.example.todoapp.repositories.comment.CommentRepository;
import com.example.todoapp.services.SpecificationService;
import com.example.todoapp.services.todo.TodoService;
import com.example.todoapp.services.user.UserService;
import com.example.todoapp.validation.CommentValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommmentService{
    private final SessionUser sessionUser;
    private final CommentRepository commentRepository;
    private final CommentValidator commentValidator;
    private final CommentMapper commentMapper;
    private final UserService userService;
    private final TodoService todoService;
    private final SpecificationService specificationService;

    @Override
    public CommentDTO create(CreateCommentDTO dto) {
        User user = sessionUser.user();
        Todo todo = todoService.findByID(dto.todoID());
        return commentMapper.toDTO(commentRepository.save(
                Comment.builder()
                        .comment(dto.comment())
                        .commentator(user)
                        .createdAt(LocalDateTime.now())
                        .todo(todo)
                        .build()
        ));
    }

    @Override
    public CommentDTO findById(Long id) {
        return null;
    }

    @Override
    public Collection<CommentDTO> findAll() {
        return commentRepository.findAll().stream().map(commentMapper::toDTO).toList();
    }

    @Override
    public Page<CommentDTO> findAll(GetCommentDTO dto) {
        Sort sort = Sort.by("createdAt").descending();
        PageRequest pageable = PageRequest.of(dto.getPage(), dto.getSize(),sort);
        Specification<Comment> specification = specificationService.getSpecification(dto);
        return commentRepository.findAllBySpecification(specification, pageable).map(commentMapper::toDTO);
    }

    @Override
    public CommentDTO update(UpdateCommentDTO dto) {
        return null;
    }

    @Override
    public void delete(DeleteCommentDTO dto) {

    }
}

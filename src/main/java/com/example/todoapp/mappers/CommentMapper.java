package com.example.todoapp.mappers;

import com.example.todoapp.domain.comment.Comment;
import com.example.todoapp.domain.todo.Todo;
import com.example.todoapp.domain.user.User;
import com.example.todoapp.dtos.comment.CommentDTO;
import com.example.todoapp.dtos.comment.CreateCommentDTO;
import com.example.todoapp.dtos.comment.UpdateCommentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CommentMapper extends GenericMapper<CreateCommentDTO, UpdateCommentDTO, CommentDTO, Comment>{
    @Override
    default CommentDTO toDTO(Comment entity){
        return new CommentDTO(
                entity.getId(),
                entity.getComment(),
                entity.getTodo().getId(),
                entity.getCommentator().getId()
        );
    }
}

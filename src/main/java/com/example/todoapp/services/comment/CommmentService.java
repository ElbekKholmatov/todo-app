package com.example.todoapp.services.comment;

import com.example.todoapp.domain.comment.Comment;
import com.example.todoapp.dtos.comment.*;
import com.example.todoapp.services.CRUDService;

public interface CommmentService extends CRUDService<Comment, CreateCommentDTO, UpdateCommentDTO, DeleteCommentDTO, CommentDTO, GetCommentDTO> {
}

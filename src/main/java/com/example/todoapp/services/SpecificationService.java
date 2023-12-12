package com.example.todoapp.services;

import com.example.todoapp.domain.comment.Comment;
import com.example.todoapp.domain.todo.Todo;
import com.example.todoapp.dtos.comment.GetCommentDTO;
import com.example.todoapp.dtos.todo.GetTodoDTO;
import com.example.todoapp.enums.Priority;
import com.example.todoapp.enums.Status;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class SpecificationService {
    public Specification<Todo> getSpecification(GetTodoDTO dto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Add criteria based on DTO fields
            if (Objects.nonNull(dto.getTitle())) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + dto.getTitle() + "%"));
            }

            if (Objects.nonNull(dto.getCreatorID())) {
                predicates.add(criteriaBuilder.equal(root.get("creator").get("id"), dto.getCreatorID()));
            }

            if (Objects.nonNull(dto.getExecutorID())) {
                predicates.add(criteriaBuilder.equal(root.get("executor").get("id"), dto.getExecutorID()));
            }

            if (Objects.nonNull(dto.getFromDate())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), dto.getFromDate()));
            }

            if (Objects.nonNull(dto.getToDate())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), dto.getToDate()));
            }
            if (Objects.nonNull(dto.getStatus())) {
                predicates.add(criteriaBuilder.equal(root.get("status"), Status.valueOf(dto.getStatus())));
            }
            if (Objects.nonNull(dto.getPriority())) {
                predicates.add(criteriaBuilder.equal(root.get("priority"), Priority.valueOf(dto.getPriority())));
            }
            // Build and return the final Predicate
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    public Specification<Comment> getSpecification(GetCommentDTO dto) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Add criteria based on DTO fields
            if (Objects.nonNull(dto.getComment())) {
                predicates.add(criteriaBuilder.like(root.get("comment"), "%" + dto.getComment() + "%"));
            }

            if (Objects.nonNull(dto.getTodoID())) {
                predicates.add(criteriaBuilder.equal(root.get("todo").get("id"), dto.getTodoID()));
            }

            if (Objects.nonNull(dto.getCommentatorID())) {
                predicates.add(criteriaBuilder.equal(root.get("commentator").get("id"), dto.getCommentatorID()));
            }

            if (Objects.nonNull(dto.getFromDate())) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), dto.getFromDate()));
            }

            if (Objects.nonNull(dto.getToDate())) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("createdAt"), dto.getToDate()));
            }
            // Build and return the final Predicate
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}

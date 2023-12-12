package com.example.todoapp.repositories.comment;

import com.example.todoapp.domain.comment.Comment;
import com.example.todoapp.domain.todo.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

    default Page<Comment> findAllBySpecification(Specification<Comment> specification, Pageable pageable) {
        return findAll(specification, pageable);
    }

}

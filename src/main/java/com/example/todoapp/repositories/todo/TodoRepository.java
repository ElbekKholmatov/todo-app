package com.example.todoapp.repositories.todo;

import com.example.todoapp.domain.todo.Todo;
import com.example.todoapp.dtos.todo.GetTodoDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TodoRepository extends JpaRepository<Todo, Long>, JpaSpecificationExecutor<Todo> {

    default Page<Todo> findAllBySpecification(Specification<Todo> specification, Pageable pageable) {
        return findAll(specification, pageable);
    }

    @Query("select t from Todo t where t.id =?1 and t.creator.id =?2")
    Optional<Todo> findByIdAndCreatorId(Long todoID, Long creatorID);
}

package com.example.todoapp.services;

import com.example.todoapp.dtos.*;
import org.springframework.data.domain.Page;

import java.util.Collection;

public interface CRUDService<T, C extends CreateDTO, U extends UpdateDTO, D extends DeleteDTO, R extends DTO, G extends GetDTO> {
    R create(C dto);

    R findById(Long id);

    Collection<R> findAll();
    Page<R> findAll(G dto);

    R update(U dto);

    void delete(D dto);
}



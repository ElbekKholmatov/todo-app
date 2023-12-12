package com.example.todoapp.mappers;

import org.mapstruct.BeanMapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import com.example.todoapp.domain.BaseEntity;
import com.example.todoapp.dtos.CreateDTO;
import com.example.todoapp.dtos.UpdateDTO;
import com.example.todoapp.dtos.error.BaseDTO;

import java.util.List;

public interface GenericMapper<CD extends CreateDTO, UD extends UpdateDTO, RD extends BaseDTO, E extends BaseEntity> extends BaseMapper {
    E toEntity(CD dto);

    RD toDTO(E entity);


    List<RD> toListDTO(List<E> entity);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    E partialUpdate(UD dto, @MappingTarget E e);

}

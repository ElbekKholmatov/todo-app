package com.example.todoapp.mappers;

import com.example.todoapp.dtos.user.UserUpdateDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;
import com.example.todoapp.domain.user.User;
import com.example.todoapp.dtos.user.UserCreateDTO;
import com.example.todoapp.dtos.user.UserDTO;


@Component
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends GenericMapper<UserCreateDTO, UserUpdateDTO, UserDTO, User> {

    @Override
    @Mapping(target = "username", source = "email")
    @Mapping(target = "password", expression = "java(new com.example.todoapp.utils.PasswordEncoderImpl().encode(dto.password()))")
    User toEntity(UserCreateDTO dto);
}

package com.hassan.store.mappers;

import com.hassan.store.dtos.CreateUserRequest;
import com.hassan.store.dtos.UpdateUserRequest;
import com.hassan.store.dtos.UserDto;
import com.hassan.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

    User toEntity(CreateUserRequest request);

    void update(UpdateUserRequest request, @MappingTarget  User user);
}

package com.hassan.store.mappers;

import com.hassan.store.dtos.UserDto;
import com.hassan.store.entities.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User user);
}

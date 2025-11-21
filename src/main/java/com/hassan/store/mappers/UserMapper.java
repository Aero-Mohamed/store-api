package com.hassan.store.mappers;

import com.hassan.store.dtos.UserDto;
import com.hassan.store.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto toDto(User user);

}

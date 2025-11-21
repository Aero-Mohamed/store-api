package com.hassan.store.mappers;

import com.hassan.store.dtos.CartDto;
import com.hassan.store.entities.Cart;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto toDto(Cart cart);
}

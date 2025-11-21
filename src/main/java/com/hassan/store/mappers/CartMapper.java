package com.hassan.store.mappers;

import com.hassan.store.dtos.CartDto;
import com.hassan.store.dtos.CartItemDto;
import com.hassan.store.entities.Cart;
import com.hassan.store.entities.CartItem;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDto toDto(Cart cart);

    @Mapping(target="totalPrice", expression = "java(cartItem.getTotalPrice())")
    CartItemDto toDto(CartItem cartItem);
}

package com.hassan.store.mappers;

import com.hassan.store.dtos.OrderDto;
import com.hassan.store.entities.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {
    OrderDto toDto(Order order);
}

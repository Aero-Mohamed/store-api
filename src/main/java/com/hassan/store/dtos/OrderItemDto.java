package com.hassan.store.dtos;

import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO for {@link com.hassan.store.entities.OrderItem}
 */
@Data
public class OrderItemDto {
    OrderProductDto product;
    Integer quantity;
    BigDecimal totalPrice;
}
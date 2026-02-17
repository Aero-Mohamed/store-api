package com.hassan.store.dtos;

import lombok.Data;

import java.math.BigDecimal;

/**
 * DTO for {@link com.hassan.store.entities.Product}
 */
@Data
public class OrderProductDto {
    Long id;
    String name;
    BigDecimal price;
}
package com.hassan.store.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class CartProductDto {
    private Long id;
    private String name;
    private BigDecimal price;
}

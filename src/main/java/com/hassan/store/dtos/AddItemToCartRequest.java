package com.hassan.store.dtos;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemToCartRequest {
    @NotNull(message = "Product id is required")
    private Long productId;

    @Min(value = 1, message = "Quantity must be greater than 0")
    private Integer quantity;
}

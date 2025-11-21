package com.hassan.store.mappers;

import com.hassan.store.dtos.ProductDto;
import com.hassan.store.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
}

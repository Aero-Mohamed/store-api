package com.hassan.store.mappers;

import com.hassan.store.dtos.ProductDto;
import com.hassan.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source="category.id", target="categoryId")
    ProductDto toDto(Product product);
}

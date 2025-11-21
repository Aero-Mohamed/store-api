package com.hassan.store.mappers;

import com.hassan.store.dtos.CreateProductRequest;
import com.hassan.store.dtos.ProductDto;
import com.hassan.store.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(source="category.id", target="categoryId")
    ProductDto toDto(Product product);

    Product toEntity(CreateProductRequest request);

    void update(CreateProductRequest request, @MappingTarget Product product);
}

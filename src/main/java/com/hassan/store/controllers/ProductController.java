package com.hassan.store.controllers;

import com.hassan.store.dtos.ProductDto;
import com.hassan.store.entities.Product;
import com.hassan.store.mappers.ProductMapper;
import com.hassan.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(
        @RequestParam(name="categoryId", required = false) Byte categoryId
    ){
        List<Product> products;

        if(categoryId != null){
            products = productRepository.findByCategoryId(categoryId);
        }else{
            products = productRepository.findAll();
        }

        return ResponseEntity.ok(
            products.stream().map(productMapper::toDto).toList()
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getProductById(@PathVariable Long id){
        Optional<Product> product = productRepository.findById(id);

        return product.map(productMapper::toDto)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

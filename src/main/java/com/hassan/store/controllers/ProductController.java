package com.hassan.store.controllers;

import com.hassan.store.dtos.ProductDto;
import com.hassan.store.entities.Product;
import com.hassan.store.mappers.ProductMapper;
import com.hassan.store.repositories.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @GetMapping
    public ResponseEntity<List<ProductDto>> getAllProducts(){
        var products = productRepository.findAll().stream()
                .map(productMapper::toDto).toList();

        return ResponseEntity.ok(products);
    }
}

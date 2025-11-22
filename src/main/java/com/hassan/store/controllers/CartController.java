package com.hassan.store.controllers;

import com.hassan.store.dtos.AddItemToCartRequest;
import com.hassan.store.dtos.CartDto;
import com.hassan.store.dtos.CartItemDto;
import com.hassan.store.dtos.UpdateCartItemRequest;
import com.hassan.store.entities.Cart;
import com.hassan.store.entities.CartItem;
import com.hassan.store.mappers.CartMapper;
import com.hassan.store.repositories.CartRepository;
import com.hassan.store.repositories.ProductRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartRepository cartRepository;
    private final CartMapper cartMapper;
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<CartDto> createCart(){
        var cart = new Cart();
        cartRepository.save(cart);

        var cartDto = cartMapper.toDto(cart);
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @PostMapping("/{id}/items")
    public ResponseEntity<CartItemDto> addToCart(
            @PathVariable(name="id") UUID cartId,
            @Valid @RequestBody AddItemToCartRequest request
    ){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }

        var product = productRepository.findById(request.getProductId()).orElse(null);
        if(product == null){
            return ResponseEntity.badRequest().build();
        }

        var cartItem = cart.addItem(product);
        cartRepository.save(cart);

        var cartItemDto = cartMapper.toDto(cartItem);

        return ResponseEntity.ok(cartItemDto);

    }

    @GetMapping("/{carId}")
    public ResponseEntity<CartDto> getCart(@PathVariable(name="carId") UUID cartId){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(cartMapper.toDto(cart));
    }


    @PutMapping("/{id}/items/{productId}")
    public ResponseEntity<CartItemDto> updateItem(
            @PathVariable(name="id") UUID cartId,
            @PathVariable(name="productId") Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
    ){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }

        var cartItem = cart.getItem(productId);
        if(cartItem == null){
            return ResponseEntity.badRequest().build();
        }

        cartItem.setQuantity(request.getQuantity());
        cartRepository.save(cart);

        return ResponseEntity.ok(cartMapper.toDto(cartItem));
    }

    @DeleteMapping("/{id}/items/{productId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable(name="id") UUID cartId,
            @PathVariable(name="productId") Long productId
    ){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null){
            return ResponseEntity.notFound().build();
        }

        cart.removeItem(productId);
        cartRepository.save(cart);

        return ResponseEntity.noContent().build();
    }
}

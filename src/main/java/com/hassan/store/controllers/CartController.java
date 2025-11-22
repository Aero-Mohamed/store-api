package com.hassan.store.controllers;

import com.hassan.store.dtos.AddItemToCartRequest;
import com.hassan.store.dtos.CartDto;
import com.hassan.store.dtos.CartItemDto;
import com.hassan.store.dtos.UpdateCartItemRequest;
import com.hassan.store.exceptions.CartNotFoundException;
import com.hassan.store.exceptions.ProductNotFoundException;
import com.hassan.store.services.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@Tag(name="Carts")
@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {

    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(){
        var cartDto = cartService.createCart();
        return new ResponseEntity<>(cartDto, HttpStatus.CREATED);
    }

    @Operation(summary = "Add item to cart")
    @PostMapping("/{id}/items")
    public ResponseEntity<CartItemDto> addToCart(
            @PathVariable(name="id") UUID cartId,
            @Valid @RequestBody AddItemToCartRequest request
    ){
        var cartItemDto = cartService.addToCart(cartId, request.getProductId());

        return ResponseEntity.ok(cartItemDto);

    }

    @GetMapping("/{carId}")
    public ResponseEntity<CartDto> getCart(@PathVariable(name="carId") UUID cartId){
        var cartDto = cartService.getCart(cartId);
        return ResponseEntity.ok(cartDto);
    }


    @PutMapping("/{id}/items/{productId}")
    public ResponseEntity<CartItemDto> updateItem(
            @PathVariable(name="id") UUID cartId,
            @PathVariable(name="productId") Long productId,
            @Valid @RequestBody UpdateCartItemRequest request
    ){
       var cartItem = cartService.updateItem(cartId, productId, request.getQuantity());
        return ResponseEntity.ok(cartItem);
    }

    @DeleteMapping("/{id}/items/{productId}")
    public ResponseEntity<Void> deleteItem(
            @PathVariable(name="id") UUID cartId,
            @PathVariable(name="productId") Long productId
    ){
        cartService.removeItem(cartId, productId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{id}/items")
    public ResponseEntity<Void> deleteAllItems(
            @PathVariable(name="id") UUID cartId
    ){
        cartService.clearCart(cartId);

        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(value = {CartNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                Map.of("message", "Cart not found.")
        );
    }

    @ExceptionHandler(value = {ProductNotFoundException.class})
    public ResponseEntity<Map<String, String>> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                Map.of("message", "Product not found in the cart.")
        );
    }
}

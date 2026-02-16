package com.hassan.store.controllers;

import com.hassan.store.dtos.CheckoutRequest;
import com.hassan.store.dtos.CheckoutResponse;
import com.hassan.store.entities.Order;
import com.hassan.store.entities.OrderItem;
import com.hassan.store.entities.OrderStatus;
import com.hassan.store.repositories.CartRepository;
import com.hassan.store.repositories.OrderRepository;
import com.hassan.store.services.AuthService;
import com.hassan.store.services.CartService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@AllArgsConstructor
@RestController
@RequestMapping("/checkout")
public class CheckoutController {

    private final CartRepository cartRepository;
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<?> checkout(
            @Valid @RequestBody CheckoutRequest request
    ){
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if(cart == null){
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Cart not found")
            );
        }

        if(cart.getItems().isEmpty()){
            return ResponseEntity.badRequest().body(
                    Map.of("error", "Cart is empty")
            );
        }

        var order = new Order();
        order.setTotalPrice(cart.getTotalPrice());
        order.setStatus(OrderStatus.PENDING);
        order.setCustomer(authService.getCurrentUser());

        cart.getItems().forEach(item -> {
            var orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProduct(item.getProduct());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setUnitPrice(item.getProduct().getPrice());
            orderItem.setTotalPrice(item.getTotalPrice());
            order.getItems().add(orderItem);
        });

        orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return ResponseEntity.ok(new CheckoutResponse(order.getId()));
    }
}

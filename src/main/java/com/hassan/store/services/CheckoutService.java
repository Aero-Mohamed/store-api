package com.hassan.store.services;

import com.hassan.store.dtos.CheckoutRequest;
import com.hassan.store.dtos.CheckoutResponse;
import com.hassan.store.entities.Order;
import com.hassan.store.exceptions.CartEmptyException;
import com.hassan.store.exceptions.CartNotFoundException;
import com.hassan.store.repositories.CartRepository;
import com.hassan.store.repositories.OrderRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CheckoutService {

    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final CartService cartService;

    public CheckoutResponse checkout(CheckoutRequest request){
        var cart = cartRepository.getCartWithItems(request.getCartId()).orElse(null);
        if(cart == null){
            throw new CartNotFoundException();
        }

        if(cart.isEmpty()){
            throw new CartEmptyException();
        }

        var order = Order.fromCart(cart, authService.getCurrentUser());

        orderRepository.save(order);
        cartService.clearCart(cart.getId());

        return new CheckoutResponse(order.getId());
    }
}

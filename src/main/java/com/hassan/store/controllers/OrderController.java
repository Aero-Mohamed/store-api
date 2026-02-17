package com.hassan.store.controllers;

import com.hassan.store.dtos.OrderDto;
import com.hassan.store.mappers.OrderMapper;
import com.hassan.store.repositories.OrderRepository;
import com.hassan.store.services.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @GetMapping
    public List<OrderDto> getAllOrders() {
        var user = authService.getCurrentUser();
        var orders = orderRepository.findAllByCustomer(user);
        return orders.stream().map(orderMapper::toDto).toList();
    }
}

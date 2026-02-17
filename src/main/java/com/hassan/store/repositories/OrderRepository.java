package com.hassan.store.repositories;

import com.hassan.store.entities.Order;
import com.hassan.store.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByCustomer(User user);
}
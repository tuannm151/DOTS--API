package com.example.DOTSAPI.repository;

import com.example.DOTSAPI.model.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepo extends JpaRepository<OrderItem, Long> {
}

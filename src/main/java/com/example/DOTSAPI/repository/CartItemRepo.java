package com.example.DOTSAPI.repository;

import com.example.DOTSAPI.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
}

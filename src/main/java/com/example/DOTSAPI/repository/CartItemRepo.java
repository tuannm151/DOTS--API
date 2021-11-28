package com.example.DOTSAPI.repository;

import com.example.DOTSAPI.model.CartItem;
import com.example.DOTSAPI.model.CartSession;
import com.example.DOTSAPI.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CartItemRepo extends JpaRepository<CartItem, Long> {
    CartItem findByIdAndCartSession(Long id, CartSession session);
    Set<CartItem> findAllByCartSession(CartSession cartSession);
}

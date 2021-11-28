package com.example.DOTSAPI.repository;

import com.example.DOTSAPI.model.CartSession;
import com.example.DOTSAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartSessionRepo extends JpaRepository<CartSession, Long> {
    CartSession findByUser(User user);
}

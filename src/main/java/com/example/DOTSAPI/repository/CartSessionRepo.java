package com.example.DOTSAPI.repository;

import com.example.DOTSAPI.model.CartSession;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartSessionRepo extends JpaRepository<CartSession, Long> {
}

package com.example.DOTSAPI.repository;

import com.example.DOTSAPI.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepo extends JpaRepository<Order, Long> {

}

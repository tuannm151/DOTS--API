package com.example.DOTSAPI.repository;

import com.example.DOTSAPI.model.Order;
import com.example.DOTSAPI.model.User;
import org.aspectj.weaver.ast.Or;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Order, Long> {
    List<Order> findAllByUserOrderByCreatedAtDesc(User user);
    Order findByIdAndUser(Long id, User user);
    List<Order> findAllByOrderByCreatedAtDesc();
}

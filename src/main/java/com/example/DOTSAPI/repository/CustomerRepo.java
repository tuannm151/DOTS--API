package com.example.DOTSAPI.repository;

import com.example.DOTSAPI.model.Customer;
import com.example.DOTSAPI.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepo extends JpaRepository<Customer, Long> {
    Customer findCustomerByIdAndUser(Long id, User user);
}
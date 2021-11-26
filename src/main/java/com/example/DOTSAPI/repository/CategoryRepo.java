package com.example.DOTSAPI.repository;

import com.example.DOTSAPI.model.Category;
import com.example.DOTSAPI.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByName(String name);
    boolean existsByName(String name);
}

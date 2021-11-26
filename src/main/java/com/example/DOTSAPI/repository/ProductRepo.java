package com.example.DOTSAPI.repository;

import com.example.DOTSAPI.model.Category;
import com.example.DOTSAPI.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory(Category category);

    @Query("SELECT p FROM Product p WHERE CONCAT(p.name, ' ', p.category.name, ' ') LIKE %?1%")
    List<Product> searchByKeyword(String keyword);
}

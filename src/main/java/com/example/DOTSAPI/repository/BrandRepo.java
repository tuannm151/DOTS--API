package com.example.DOTSAPI.repository;

import com.example.DOTSAPI.model.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepo extends JpaRepository<Brand, Long> {
    Brand findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}
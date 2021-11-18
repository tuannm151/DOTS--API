package com.example.DOTSAPI.services;

import com.example.DOTSAPI.model.Product;

import java.util.List;

public interface ProductServices {
    Product saveProduct(Product product);
    Product getProductById(Long id);
    Product updateProduct(Product product);
    List<Product> getAllProducts();
    List<Product> findByCategoryName(String categoryName);
    void deleteProduct(Long id);
}

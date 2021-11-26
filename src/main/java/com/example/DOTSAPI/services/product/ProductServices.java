package com.example.DOTSAPI.services.product;

import com.example.DOTSAPI.model.Category;
import com.example.DOTSAPI.model.Product;

import java.util.List;

public interface ProductServices {
    Product saveProduct(Product product);
    Product getProductById(Long id);
    Product updateProductById(Long id, Product product);
    List<Product> getAllProducts();
    List<Product> searchByKeyword(String keyword);
    List<Product> findAllByCategoryName(String categoryName);
    void deleteProduct(Long id);
    Category saveCategory(Category category);
    List<Category> getAllCategories();
}

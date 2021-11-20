package com.example.DOTSAPI.services;

import com.example.DOTSAPI.model.Category;
import com.example.DOTSAPI.model.Product;
import com.example.DOTSAPI.repository.CategoryRepo;
import com.example.DOTSAPI.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Transactional
public class ProductServicesImpl implements ProductServices {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    @Override
    public Product saveProduct(Product product) {
        return productRepo.save(product);
    }

    @Override
    public Product getProductById(Long id) {
        return productRepo.getById(id);
    }

    @Override
    public List<Product> findByCategoryName(String categoryName) {
        Category category = categoryRepo.findByName(categoryName);
        return  productRepo.findByCategory(category);
    }
    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }
    @Override
    public Product updateProduct(Product product) {
        return null;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
}

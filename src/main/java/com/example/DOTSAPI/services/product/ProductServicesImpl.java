package com.example.DOTSAPI.services.product;

import com.example.DOTSAPI.exception.AlreadyExistsException;
import com.example.DOTSAPI.exception.NotFoundException;
import com.example.DOTSAPI.model.Category;
import com.example.DOTSAPI.model.Product;
import com.example.DOTSAPI.repository.CategoryRepo;
import com.example.DOTSAPI.repository.ProductRepo;
import com.example.DOTSAPI.services.product.ProductServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional
public class ProductServicesImpl implements ProductServices {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    @Override
    public Product saveProduct(Product product) {
        String categoryName = product.getCategory().getName();
        Category category = categoryRepo.findByName(categoryName);
        if(category == null) {
            throw new NotFoundException("Category not found");
        }
        product.setCategory(category);
        return productRepo.save(product);
    }

    @Override
    public Product getProductById(Long id) {
            Optional<Product> product =  productRepo.findById(id);
            if(product.isEmpty()) {
               throw new NotFoundException("Product ID not found");
            }
            return product.get();
    }

    @Override
    public List<Product> findAllByCategoryName(String categoryName) {
        Category category = categoryRepo.findByName(categoryName);
        if(category == null) throw new NotFoundException("Category not found");
        return  productRepo.findAllByCategory(category);
    }

    @Override
    public List<Product> searchByKeyword(String keyword) {
        return productRepo.searchByKeyword(keyword);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepo.findAll();
    }

    @Override
    public Product updateProductById(Long id, Product newProduct) {
        if(!productRepo.existsById(id)) throw new NotFoundException("Product not found");
        Category category = categoryRepo.findByName(newProduct.getCategory().getName());
        if(category == null) throw new NotFoundException("Category not found");

        Product product = productRepo.getById(id);
        product.setName(newProduct.getName());
        product.setDescription(newProduct.getDescription());
        product.setImageUrl(newProduct.getImageUrl());
        product.setColor(newProduct.getColor());
        product.setPrice(newProduct.getPrice());
        product.setCategory(category);
        product.setStock(newProduct.getStock());
        return productRepo.save(product);
    }

    @Override
    public Category saveCategory(Category category) {
        if(categoryRepo.existsByName(category.getName())) {
            throw new AlreadyExistsException("Category has already existed");
        }
        return categoryRepo.save(category);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
}

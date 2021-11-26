package com.example.DOTSAPI.controller;

import com.example.DOTSAPI.model.Category;
import com.example.DOTSAPI.model.Product;
import com.example.DOTSAPI.services.product.ProductServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {
    private final ProductServices productServices;

    @GetMapping(value = "", params = "id")
    public ResponseEntity<Product> getProductById(@RequestParam Long id) {
        return ResponseEntity.ok().body(productServices.getProductById(id));
    }
    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategories() {
        return ResponseEntity.ok().body(productServices.getAllCategories());
    }

    @GetMapping(value = "/list", params="category")
    public ResponseEntity<List<Product>> findAllByCategoryName(@RequestParam String category) {
        return ResponseEntity.ok().body(productServices.findAllByCategoryName(category));
    }

    @GetMapping(value = "/list/search", params="keyword")
    public ResponseEntity<List<Product>> findAllByName(@RequestParam String keyword) {
        if(keyword.length() > 40) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(productServices.searchByKeyword(keyword));
    }

    @GetMapping("/admin/list")
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok().body(productServices.getAllProducts());
    }
    @PostMapping("/admin/category/save")
    public ResponseEntity<Category> saveCategory(@RequestBody @Valid Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productServices.saveCategory(category));
    }
    @PostMapping("/admin/save")
    public ResponseEntity<Product> saveProduct(@RequestBody @Valid Product product) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productServices.saveProduct(product));
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody @Valid Product product) {
        return ResponseEntity.ok().body(productServices.updateProductById(id, product));
    }

    @DeleteMapping( "/admin/delete/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable Long id) {
        productServices.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

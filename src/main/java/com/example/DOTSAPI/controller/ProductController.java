package com.example.DOTSAPI.controller;

import com.example.DOTSAPI.dto.product.ProductDto;
import com.example.DOTSAPI.dto.product.ResponseProductDto;
import com.example.DOTSAPI.dto.product.RateProductDto;
import com.example.DOTSAPI.model.Brand;
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
    public ResponseEntity<ResponseProductDto> getProductById(@RequestParam Long id) {
        return ResponseEntity.ok().body(productServices.getProductById(id));
    }
    @GetMapping("/category")
    public ResponseEntity<List<Category>> getAllCategoriesName() {
        return ResponseEntity.ok().body(productServices.getAllCategories());
    }

    @GetMapping("/brand")
    public ResponseEntity<List<Brand>> getAllBrandsName() {
        return ResponseEntity.ok().body(productServices.getAllBrands());
    }

    @GetMapping(value = "/list", params="category")
    public ResponseEntity<List<ResponseProductDto>> findAllByCategoryName(@RequestParam String category) {
        return ResponseEntity.ok().body(productServices.findAllByCategoryName(category));
    }

    @GetMapping(value = "/list/search", params="keyword")
    public ResponseEntity<List<ResponseProductDto>> findAllByName(@RequestParam String keyword) {
        if(keyword.length() > 40) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok().body(productServices.searchByKeyword(keyword));
    }

    @GetMapping("/list")
    public ResponseEntity<List<ResponseProductDto>> getAllProducts() {
        return ResponseEntity.ok().body(productServices.getAllProducts());
    }
    @PostMapping("/admin/category/save")
    public ResponseEntity<Category> saveCategory(@RequestBody @Valid Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productServices.saveCategory(category));
    }
    @PostMapping("/admin/brand/save")
    public ResponseEntity<Brand> saveBrand(@RequestBody @Valid Brand brand) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productServices.saveBrand(brand));
    }
    @PostMapping("/admin/save")
    public ResponseEntity<ResponseProductDto> saveProduct(@RequestBody @Valid ProductDto productDto) {
        productServices.saveProduct(productDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/admin/rating")
    public ResponseEntity<?> setRatingProduct(@RequestBody @Valid RateProductDto rateProductDto) {
        productServices.rateProduct(rateProductDto);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/admin/update/{id}")
    public ResponseEntity<ResponseProductDto> updateProduct(@PathVariable Long id, @RequestBody @Valid ProductDto productDto) {
        return ResponseEntity.ok().body(productServices.updateProductById(id, productDto));
    }

    @DeleteMapping( "/admin/delete/{id}")
    public ResponseEntity<Product> deleteProductById(@PathVariable Long id) {
        productServices.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

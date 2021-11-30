package com.example.DOTSAPI.services.product;

import com.example.DOTSAPI.dto.product.ProductDto;
import com.example.DOTSAPI.dto.product.ResponseProductDto;
import com.example.DOTSAPI.dto.product.RateProductDto;
import com.example.DOTSAPI.model.Brand;
import com.example.DOTSAPI.model.Category;
import com.example.DOTSAPI.model.Product;

import java.util.List;

public interface ProductServices {
    ResponseProductDto saveProduct(ProductDto productDto);
    ResponseProductDto getProductById(Long id);
    ResponseProductDto updateProductById(Long id, ProductDto productDto);
    List<ResponseProductDto> getAllProducts();
    List<ResponseProductDto> searchByKeyword(String keyword);
    List<ResponseProductDto> findAllByCategoryName(String categoryName);
    void deleteProduct(Long id);
    Brand saveBrand(Brand brand);
    Category saveCategory(Category category);
    List<Brand> getAllBrands();
    List<Category> getAllCategories();
    void rateProduct(RateProductDto rateProductDto);
}

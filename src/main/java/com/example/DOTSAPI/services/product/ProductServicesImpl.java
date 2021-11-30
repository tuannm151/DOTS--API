package com.example.DOTSAPI.services.product;

import com.example.DOTSAPI.dto.product.ProductDto;
import com.example.DOTSAPI.dto.product.ResponseProductDto;
import com.example.DOTSAPI.dto.product.RateProductDto;
import com.example.DOTSAPI.exception.AlreadyExistsException;
import com.example.DOTSAPI.exception.NotFoundException;
import com.example.DOTSAPI.model.Brand;
import com.example.DOTSAPI.model.Category;
import com.example.DOTSAPI.model.Product;
import com.example.DOTSAPI.repository.BrandRepo;
import com.example.DOTSAPI.repository.CategoryRepo;
import com.example.DOTSAPI.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service @RequiredArgsConstructor @Transactional
public class ProductServicesImpl implements ProductServices {
    private final ProductRepo productRepo;
    private final CategoryRepo categoryRepo;
    private final BrandRepo brandRepo;
    @Override
    public ResponseProductDto saveProduct(ProductDto productDto) {
        Product product = convertToEntity(productDto, new Product());
        productRepo.save(product);
        return new ResponseProductDto(product);
    }

    @Override
    public ResponseProductDto getProductById(Long id) {
            Optional<Product> product =  productRepo.findById(id);
            if(product.isEmpty()) {
               throw new NotFoundException("PRODUCT_NOT_FOUND");
            }
            return new ResponseProductDto(product.get());
    }

    @Override
    public List<ResponseProductDto> findAllByCategoryName(String categoryName) {
        Category category = categoryRepo.findByNameIgnoreCase(categoryName);
        if(category == null) throw new NotFoundException("CATEGORY_NOT_FOUND");
        List<Product> products = productRepo.findAllByCategory(category);
        List<ResponseProductDto> responseProductDtos = new ArrayList<>();
        for(Product product : products) {
            responseProductDtos.add(new ResponseProductDto(product));
        }
        return responseProductDtos;
    }

    @Override
    public List<ResponseProductDto> searchByKeyword(String keyword) {
        List<Product> products = productRepo.searchByKeyword(keyword.toLowerCase());;
        List<ResponseProductDto> responseProductDtos = new ArrayList<>();
        for(Product product : products) {
            responseProductDtos.add(new ResponseProductDto(product));
        }
        return responseProductDtos;
    }

    @Override
    public List<ResponseProductDto> getAllProducts() {
        List<Product> products = productRepo.findAll();;
        List<ResponseProductDto> responseProductDtos = new ArrayList<>();
        for(Product product : products) {
            ResponseProductDto responseProductDto = new ResponseProductDto(product);
            responseProductDto.setCreatedAt(product.getCreatedAt());
            responseProductDto.setModifiedAt(product.getModifiedAt());
            responseProductDtos.add(responseProductDto);
        }
        return responseProductDtos;
    }

    @Override
    public ResponseProductDto updateProductById(Long id, ProductDto productDto) {
        if(!productRepo.existsById(id)) throw new NotFoundException("PRODUCT_NOT_FOUND");
        Product product = productRepo.getById(id);
        Product updatedProduct = convertToEntity(productDto, product);
        updatedProduct.setModifiedAt(new Date());
        return new ResponseProductDto(productRepo.save(updatedProduct));
    }

    @Override
    public Category saveCategory(Category category) {
        if(categoryRepo.existsByNameIgnoreCase(category.getName())) {
            throw new AlreadyExistsException("CATEGORY_EXIST");
        }
        return categoryRepo.save(category);
    }

    @Override
    public Brand saveBrand(Brand brand) {
        if(brandRepo.existsByNameIgnoreCase(brand.getName())) {
            throw new AlreadyExistsException("CATEGORY_EXIST");
        }
        return brandRepo.save(brand);
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepo.findAll();
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepo.findAll();
    }

    @Override
    public void rateProduct(RateProductDto rateProductDto) {
        Optional<Product> productFind =  productRepo.findById(rateProductDto.getProductId());
        if(productFind.isEmpty()) {
            throw new NotFoundException("PRODUCT_NOT_FOUND");
        }
        Product product = productFind.get();
        float overallRating = product.getOverallRating();
        int totalRating = product.getTotalRating();
        float newOverallRating = ((overallRating*totalRating)+rateProductDto.getRating())/(totalRating+1);
        product.setOverallRating(newOverallRating);
        product.setTotalRating(totalRating+1);
    }

    Product convertToEntity(ProductDto productDto, Product productEntity) {
        Category category = categoryRepo.findByNameIgnoreCase(productDto.getCategory());
        if(category == null) throw new NotFoundException("CATEGORY_NOT_FOUND");
        Brand brand = brandRepo.findByNameIgnoreCase(productDto.getBrand());
        if(brand == null
        ) throw new NotFoundException("BRAND_NOT_FOUND");

        productEntity.setName(productDto.getProductName());

        if(productDto.getDescription() != null) {
            productEntity.setDescription(productDto.getDescription());
        }
        productEntity.setImageUrl(productDto.getImageUrl());
        productEntity.setPrice(productDto.getUnitPrice());
        productEntity.setStock(productDto.getStock());
        productEntity.setTotalRating(productDto.getTotalRating());
        productEntity.setOverallRating(productDto.getOverallRating());
        productEntity.setSize(productDto.getSize());
        productEntity.setColor(productDto.getColor());
        productEntity.setCategory(category);
        productEntity.setBrand(brand);
        return productEntity;
    }

    @Override
    public void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
}

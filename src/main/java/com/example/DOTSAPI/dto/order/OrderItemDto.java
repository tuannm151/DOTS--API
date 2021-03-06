package com.example.DOTSAPI.dto.order;

import com.example.DOTSAPI.model.OrderItem;
import com.example.DOTSAPI.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor

public class OrderItemDto {
    private Long productId;
    private String category;
    private String imageUrl;
    private String productName;
    private Long quantity;
    private Integer size;
    private double unitPrice;
    private String color;
    private String brand;

    public OrderItemDto(Product product) {
        this.imageUrl = product.getImageUrl();
        this.productName = product.getName();
        this.productId = product.getId();
        this.category = product.getCategory().getName();
        this.brand = product.getBrand().getName();
    }
}

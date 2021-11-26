package com.example.DOTSAPI.dto.order;

import com.example.DOTSAPI.model.OrderItem;
import com.example.DOTSAPI.model.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class OrderItemDto {
    private Long id;

    @NotNull
    private Product product;

    @NotNull
    private Integer quantity;

    @NotNull
    private double unitPrice;

    @NotNull
    private String color;

    public OrderItemDto(OrderItem orderItem) {
        this.product = orderItem.getProduct();
        this.quantity = orderItem.getQuantity();
        this.unitPrice = orderItem.getUnitPrice();
        this.color = orderItem.getColor();
    }
}

package com.example.DOTSAPI.dto.cart;

import com.example.DOTSAPI.model.CartItem;
import com.example.DOTSAPI.model.Product;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartItemDto {
    private Long id;

    @NotNull
    private Product product;

    @NotBlank
    private String color;

    @NotNull
    private Integer quantity;

    public CartItemDto(CartItem cartItem) {
        this.product = cartItem.getProduct();
        this.color = cartItem.getColor();
        this.quantity = cartItem.getQuantity();
    }
}

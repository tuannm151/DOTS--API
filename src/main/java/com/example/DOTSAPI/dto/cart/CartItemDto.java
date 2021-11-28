package com.example.DOTSAPI.dto.cart;

import com.example.DOTSAPI.model.CartItem;
import com.example.DOTSAPI.model.Product;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartItemDto {
    @NotNull
    private Long cartItemId;

    @NotNull
    private Long quantity;

    @NotNull
    private Integer size;

    @NotBlank
    private String color;

    @NotNull
    private Product product;

    public CartItemDto(CartItem cartItem) {
        this.cartItemId = cartItem.getId();
        this.size = cartItem.getSize();
        this.product = cartItem.getProduct();
        this.color = cartItem.getColor();
        this.quantity = cartItem.getQuantity();
    }
}

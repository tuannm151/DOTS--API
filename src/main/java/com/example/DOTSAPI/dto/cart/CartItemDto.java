package com.example.DOTSAPI.dto.cart;

import com.example.DOTSAPI.dto.product.ResponseProductDto;
import com.example.DOTSAPI.model.CartItem;
import com.example.DOTSAPI.model.Product;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class CartItemDto {
    private Long cartItemId;
    private Long quantity;
    private Integer size;
    private String color;
    private ResponseProductDto responseProductDto;

    @JsonIgnore
    private Product product;

    public CartItemDto(CartItem cartItem) {
        this.cartItemId = cartItem.getId();
        this.size = cartItem.getSize();
        this.responseProductDto = new ResponseProductDto(cartItem.getProduct());
        this.color = cartItem.getColor();
        this.quantity = cartItem.getQuantity();
    }
}

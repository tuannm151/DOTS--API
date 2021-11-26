package com.example.DOTSAPI.dto.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CartSessionDto {
    private Long id;

    private Set<CartItemDto> cartItemsDto;

    private double totalPrice;

   public CartSessionDto(Set<CartItemDto> cartItemsDto, double totalPrice) {
       this.cartItemsDto = cartItemsDto;
       this.totalPrice = totalPrice;
   }
}

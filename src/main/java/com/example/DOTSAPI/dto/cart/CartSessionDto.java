package com.example.DOTSAPI.dto.cart;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
public class CartSessionDto {
    private double totalPrice;
    private int itemsNumber;
    private Set<CartItemDto> cartItemsDto;

   public CartSessionDto(Set<CartItemDto> cartItemsDto, double totalPrice) {
       this.cartItemsDto = cartItemsDto;
       this.totalPrice = totalPrice;
       this.itemsNumber = cartItemsDto.size();
   }
}

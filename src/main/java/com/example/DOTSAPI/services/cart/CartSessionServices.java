package com.example.DOTSAPI.services.cart;

import com.example.DOTSAPI.dto.cart.AddToCardDto;
import com.example.DOTSAPI.model.AppUser;

public interface CartSessionServices {
    void addToCart(AddToCardDto addToCardDto);
    void updateCartItem(AddToCardDto addToCardDto);
    void listCartItems(AppUser appUser);
    void deleteCartItem(Long productId, AppUser appUser);
}

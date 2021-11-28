package com.example.DOTSAPI.services.cart;

import com.example.DOTSAPI.dto.cart.AddToCardDto;
import com.example.DOTSAPI.dto.cart.CartSessionDto;
import com.example.DOTSAPI.dto.cart.UpdateCartItemDto;
import com.example.DOTSAPI.model.User;

import javax.naming.OperationNotSupportedException;

public interface CartSessionServices {
    void addToCart(AddToCardDto addToCardDto, User user) throws OperationNotSupportedException;
    void updateCartItem(UpdateCartItemDto updateCartItemDto, User user) throws OperationNotSupportedException;
    CartSessionDto listCartItems(User user);
    void deleteCartItem(Long cartItemId, User user);
    void deleteUserAllCartItems(User user);
}

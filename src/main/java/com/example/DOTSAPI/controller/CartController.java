package com.example.DOTSAPI.controller;

import com.example.DOTSAPI.dto.cart.AddToCardDto;
import com.example.DOTSAPI.dto.cart.CartSessionDto;
import com.example.DOTSAPI.dto.cart.UpdateCartItemDto;
import com.example.DOTSAPI.exception.CustomAuthenticationException;
import com.example.DOTSAPI.model.User;
import com.example.DOTSAPI.services.AuthServices;
import com.example.DOTSAPI.services.appUser.AppUserServices;
import com.example.DOTSAPI.services.cart.CartSessionServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.naming.OperationNotSupportedException;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {
    private final CartSessionServices cartSessionServices;
    private final AppUserServices appUserServices;

    @GetMapping(value = "/list")
    public ResponseEntity<CartSessionDto> listCartItems(Authentication authentication) {
        User user = appUserServices.findUserByUserName(authentication.getName());
        return ResponseEntity.ok().body(cartSessionServices.listCartItems(user));
    }

    @PostMapping(value = "/add")
    public ResponseEntity<?> addToCart(@RequestBody @Valid AddToCardDto addToCardDto, Authentication authentication) throws CustomAuthenticationException, OperationNotSupportedException {
        User user = appUserServices.findUserByUserName(authentication.getName());
        cartSessionServices.addToCart(addToCardDto, user);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(value = "/update")
    public ResponseEntity<?> updateCartItem(@RequestBody @Valid UpdateCartItemDto updateCartItemDto,
                                            Authentication authentication) throws OperationNotSupportedException {
        User user = appUserServices.findUserByUserName(authentication.getName());
        cartSessionServices.updateCartItem(updateCartItemDto, user);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping (value = "/delete/{id}")
    public ResponseEntity<?> deleteCartItem(@PathVariable Long id, Authentication authentication) {
        User user = appUserServices.findUserByUserName(authentication.getName());
        cartSessionServices.deleteCartItem(id, user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping (value = "/delete")
    public ResponseEntity<?> deleteUserCartItems(Authentication authentication)  {
        User user = appUserServices.findUserByUserName(authentication.getName());
        cartSessionServices.deleteUserAllCartItems(user);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

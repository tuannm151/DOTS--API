package com.example.DOTSAPI.services.cart;

import com.example.DOTSAPI.dto.cart.AddToCardDto;
import com.example.DOTSAPI.dto.cart.CartItemDto;
import com.example.DOTSAPI.dto.cart.CartSessionDto;
import com.example.DOTSAPI.dto.cart.UpdateCartItemDto;
import com.example.DOTSAPI.exception.NotFoundException;
import com.example.DOTSAPI.model.User;
import com.example.DOTSAPI.model.CartItem;
import com.example.DOTSAPI.model.CartSession;
import com.example.DOTSAPI.model.Product;
import com.example.DOTSAPI.repository.CartItemRepo;
import com.example.DOTSAPI.repository.CartSessionRepo;
import com.example.DOTSAPI.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.naming.OperationNotSupportedException;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Transactional
public class CartSessionServicesImpl implements CartSessionServices {
    private final ProductRepo productRepo;
    private final CartItemRepo cartItemRepo;
    private final CartSessionRepo cartSessionRepo;
    @Override
    public void addToCart(AddToCardDto addToCardDto, User user) throws OperationNotSupportedException {
        Product product =
                productRepo.findById(addToCardDto.getProductId()).orElseThrow(()->new NotFoundException(
                        "PRODUCT_NOT_FOUND"));
        if(!product.getSize().contains(addToCardDto.getSize())) {
            throw new NotFoundException("SIZE_NOT_AVAILABLE");
        }
        if(!product.getColor().contains(addToCardDto.getColor())) {
            throw new NotFoundException("COLOR_NOT_AVAILABLE");
        }
        CartSession cartSession = user.getCartSession();

        long addToCartQuantity = addToCardDto.getQuantity();
        long totalQuantity = 0;
        CartItem cartItemExists = null;
        for(CartItem cartItem : cartSession.getCartItems()) {
            if(cartItem.getProduct().equals(product)) {
                totalQuantity += cartItem.getQuantity();
                if(cartItem.getColor().equals(addToCardDto.getColor())
                && cartItem.getSize().equals(addToCardDto.getSize())) {
                    cartItemExists = cartItem;
                }
            }
        }
        if(totalQuantity + addToCartQuantity > product.getStock()) {
            throw new OperationNotSupportedException("QUANTITY_EXCEEDED_STOCK");
        }
        if(cartItemExists != null) {
            cartItemExists.setQuantity(cartItemExists.getQuantity() + addToCartQuantity);
            return;
        }

//        CartItem existCartItem = cartItemRepo.findByProductAndColorAndSize(product, addToCardDto.getColor(),
//                addToCardDto.getSize());
//        if(existCartItem != null) {
//            int totalQuantity = addToCardDto.getQuantity() + existCartItem.getQuantity();
//            if(totalQuantity > product.getStock()) {
//                throw new OperationNotSupportedException("QUANTITY_EXCEED_STOCK");
//            }
//            existCartItem.setQuantity(totalQuantity);
//            return;
//        }

        if(addToCardDto.getQuantity() > product.getStock()) {
            throw new OperationNotSupportedException("QUANTITY_EXCEEDED_STOCK");
        }
        CartItem cartItem = new CartItem(addToCardDto.getQuantity(), addToCardDto.getSize(), addToCardDto.getColor(),
                product);
        cartItem.setCartSession(cartSession);
        cartItemRepo.save(cartItem);
    }

    @Override
    public void updateCartItem(UpdateCartItemDto updateCartItemDto, User user) throws OperationNotSupportedException {

        CartSession cartSession = user.getCartSession();
        CartItem cartItem = cartItemRepo.findByIdAndCartSession(updateCartItemDto.getCartItemId(), cartSession);
        if(cartItem == null) {
            throw new NotFoundException("CART_ITEM_NOT_FOUND");
        }
        Product product = cartItem.getProduct();
        if(updateCartItemDto.getQuantity() > product.getStock()) {
            throw new OperationNotSupportedException("QUANTITY_EXCEEDED_STOCK");
        }
        if(!product.getSize().contains(updateCartItemDto.getSize())) {
            throw new NotFoundException("SIZE_NOT_AVAILABLE");
        }
        if(!product.getColor().contains(updateCartItemDto.getColor())) {
            throw new NotFoundException("COLOR_NOT_AVAILABLE");
        }

        cartItem.setSize(updateCartItemDto.getSize());
        cartItem.setColor(updateCartItemDto.getColor());
        cartItem.setQuantity(updateCartItemDto.getQuantity());
        cartItemRepo.save(cartItem);
    }

    @Override
    public CartSessionDto listCartItems(User user) {
        Set<CartItem> cartItems = cartItemRepo.findAllByCartSession(user.getCartSession());
        Set<CartItemDto> cartItemsDto = new HashSet<>();
        for(CartItem cartItem : cartItems) {
            CartItemDto cartItemDto = new CartItemDto(cartItem);
            cartItemsDto.add(cartItemDto);
        }

        double totalPrice = 0.0;
        for (CartItem cartItem : cartItems) {
            totalPrice+= cartItem.getQuantity() * cartItem.getProduct().getPrice();
        }

        return new CartSessionDto(cartItemsDto, totalPrice);
    }

    @Override
    public void deleteCartItem(Long cartItemId, User user) {
        CartSession cartSession = user.getCartSession();
        CartItem cartItem = cartItemRepo.findByIdAndCartSession(cartItemId, cartSession);
        if(cartItem == null) {
            throw new NotFoundException("CART_ITEM_NOT_FOUND");
        }
        cartItem.setCartSession(null);
        cartItemRepo.save(cartItem);
    }

    @Override
    public void deleteUserAllCartItems(User user) {
        user.getCartSession().getCartItems().clear();
    }
}

package com.BikkadIT.services;

import com.BikkadIT.dtos.AddItemToCartRequest;
import com.BikkadIT.dtos.CartDto;

public interface CartI {

    CartDto addItems(long userId, AddItemToCartRequest request);

    void removeItemFromCart(long userId,long cartItem);

    void clearCart(long userId);

    CartDto getCartByUser(long userId);
}

package com.BikkadIT.controller;

import com.BikkadIT.dtos.AddItemToCartRequest;
import com.BikkadIT.dtos.ApiResponse;
import com.BikkadIT.dtos.CartDto;
import com.BikkadIT.services.Impl.CartImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Autowired
    private CartImpl cartImpl;
@PostMapping("/{userId}")
    public ResponseEntity<CartDto> addItemToCart(@RequestBody AddItemToCartRequest request, @PathVariable long userId){
        CartDto cartDto = this.cartImpl.addItems(userId, request);
        return new ResponseEntity<CartDto>(cartDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse> removeItemFromCart(@PathVariable long userId,@PathVariable long itemId){
       this.cartImpl.removeItemFromCart(userId,itemId);
        ApiResponse apiResponse = ApiResponse.builder().message("Item is removed")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse> clearCart(@PathVariable long userId){
        this.cartImpl.clearCart(userId);
        ApiResponse apiResponse = ApiResponse.builder().message("Cart is blank")
                .success(true)
                .status(HttpStatus.OK)
                .build();
        return new ResponseEntity<>(apiResponse,HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<CartDto> getCart(@PathVariable long userId){
        CartDto cartDto = this.cartImpl.getCartByUser(userId);
        return new ResponseEntity<CartDto>(cartDto, HttpStatus.CREATED);
    }
}

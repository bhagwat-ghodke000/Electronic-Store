package com.BikkadIT.services.Impl;

import com.BikkadIT.dtos.AddItemToCartRequest;
import com.BikkadIT.dtos.CartDto;
import com.BikkadIT.entity.Cart;
import com.BikkadIT.entity.CartItems;
import com.BikkadIT.entity.Product;
import com.BikkadIT.entity.User;
import com.BikkadIT.exception.BadRequestException;
import com.BikkadIT.exception.ResourceNotFoundException;
import com.BikkadIT.repository.CartItemRepo;
import com.BikkadIT.repository.CartRepo;
import com.BikkadIT.repository.ProductRepo;
import com.BikkadIT.repository.UserRepo;
import com.BikkadIT.services.CartI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartImpl implements CartI {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartItemRepo cartItemRepo;
    @Override
    public CartDto addItems(long userId, AddItemToCartRequest request) {
        int quantity = request.getQuantity();
        long productId = request.getProductId();

        if (quantity <= 0) {

            throw new BadRequestException("Requested quantity is not valid");
        }
        Product product = productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("This Product does not exit"));

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User does not Exist"));
        Cart cart=null;
        try {

            cart=this.cartRepo.findByUser(user).get();

        }catch (NoSuchElementException ex){
            cart=new Cart();
            cart.setCreatedAt(new Date());
        }

        List<CartItems> items = cart.getItems();

       // boolean updated=false;
        AtomicReference<Boolean> updated=new AtomicReference<>(false);

        items = items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getPrice());
                updated.set(true);
            }
            return item;
        }).collect(Collectors.toList());

       // cart.setItems(updatedItems);

        if(!updated.get()) {
            CartItems cartItems = CartItems.builder().quantity(quantity)
                    .totalPrice(quantity * product.getPrice())
                    .cart(cart)
                    .product(product)
                    .build();

            cart.getItems().add(cartItems);
        }
        cart.setUser(user);
        Cart updatedCart = this.cartRepo.save(cart);
        CartDto cartDto = this.modelMapper.map(updatedCart, CartDto.class);
        return cartDto;
    }

    @Override
    public void removeItemFromCart(long userId, long cartItem) {

        CartItems cartItem1 = this.cartItemRepo.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("Cart is not Found"));
        this.cartItemRepo.delete(cartItem1);


    }

    @Override
    public void clearCart(long userId) {

        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User is not found"));
        Cart cart = cartRepo.findByUser(user).get();
        cart.getItems().clear();
        cartRepo.save(cart);
    }

    @Override
    public CartDto getCartByUser(long userId) {
        User user = userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User is not found"));
        Cart cart = cartRepo.findByUser(user).get();
        return this.modelMapper.map(cart,CartDto.class);
    }
}

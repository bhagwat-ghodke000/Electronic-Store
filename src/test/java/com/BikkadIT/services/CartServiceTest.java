package com.BikkadIT.services;

import com.BikkadIT.dtos.AddItemToCartRequest;
import com.BikkadIT.dtos.CartDto;
import com.BikkadIT.entity.Cart;
import com.BikkadIT.entity.CartItems;
import com.BikkadIT.entity.Product;
import com.BikkadIT.entity.User;
import com.BikkadIT.repository.CartRepo;
import com.BikkadIT.repository.ProductRepo;
import com.BikkadIT.repository.UserRepo;
import com.BikkadIT.services.Impl.CartImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CartServiceTest {
    @MockBean
    private CartRepo cartRepo;
    @MockBean
    private ProductRepo productRepo;
    @MockBean
    private UserRepo userRepo;
    @Autowired
    private CartImpl cartImpl;
    @Autowired
    private ModelMapper modelMapper;

    Product product;

    User user;

    Cart cart;

    long productId;
    long userId;
    @Test
    public void addItems(){
        product = Product.builder().title("Mobile")
                .decription("This is Samsung 5G Mobile")
                .stock(true)
                .live(true)
                .price(20000)
                .quantity(8)
                .build();
        productId=12;

        Mockito.when(this.productRepo.findById(productId)).thenReturn(Optional.of(product));

        user = User.builder().name("bhagwat")
                .email("bg000@gmail.com")
                .about("This is first user")
                .gender("Male")
                .password("Bg@12345")
                .imageName("abc.png")
                .build();

        userId=10;

        CartItems items=CartItems.builder().product(product)
                .cart(cart)
                .cartItemId(3)
                .quantity(4)
                .totalPrice(2000).build();

        CartItems items1=CartItems.builder().product(product)
                .cart(cart)
                .cartItemId(3)
                .quantity(4)
                .totalPrice(1500).build();

        List<CartItems> list = new ArrayList<>();
        list.add(items);
        list.add(items1);

        cart=Cart.builder().user(user)
                        .items(list)
                                .cartId(3)
                                        .build();


        Mockito.when(this.userRepo.findById(userId)).thenReturn(Optional.of(user));
        Mockito.when(this.cartRepo.findByUser(Mockito.any())).thenReturn(Optional.of(cart));

        AddItemToCartRequest request=AddItemToCartRequest.builder().productId(productId)
                .quantity(5).build();

        CartDto cartDto = this.cartImpl.addItems(userId, request);
        System.out.println(cartDto.getItems());
        System.out.println(cartDto.getUser());
        Assertions.assertNotNull(cartDto);
    }
}

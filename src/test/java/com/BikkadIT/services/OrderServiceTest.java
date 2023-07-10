package com.BikkadIT.services;

import com.BikkadIT.entity.Cart;
import com.BikkadIT.entity.CartItems;
import com.BikkadIT.entity.Product;
import com.BikkadIT.entity.User;
import com.BikkadIT.repository.CartRepo;
import com.BikkadIT.repository.OrederRepo;
import com.BikkadIT.repository.UserRepo;
import com.BikkadIT.services.Impl.OrderImpl;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.Optional;

@SpringBootTest
public class OrderServiceTest {
    @MockBean
    private OrederRepo  orderRepo;
    @MockBean
    private UserRepo userRepo;
    @MockBean
    private CartRepo cartRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private OrderImpl orderImpl;

    User user;
    Cart cart;
    long userId=0;
   Product product;
   long productId;

}

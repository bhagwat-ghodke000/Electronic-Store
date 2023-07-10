package com.BikkadIT.services.Impl;

import com.BikkadIT.dtos.OrderDto;
import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.dtos.ProductDto;
import com.BikkadIT.entity.*;
import com.BikkadIT.exception.BadRequestException;
import com.BikkadIT.exception.ResourceNotFoundException;
import com.BikkadIT.helper.CustomPagenation;
import com.BikkadIT.repository.CartRepo;
import com.BikkadIT.repository.OrederRepo;
import com.BikkadIT.repository.UserRepo;
import com.BikkadIT.services.OrderI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.instrument.UnmodifiableClassException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class OrderImpl implements OrderI {
    @Autowired
    private CartRepo cartRepo;
    @Autowired
    private UserRepo userRpo;
    @Autowired
    private OrederRepo orderRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public OrderDto creteOrder(OrderDto orderDto, long userId,long cartId) {

        User user = this.userRpo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("This User is not found"));
        Cart cart = this.cartRepo.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("This cart is not valid"));
        List<CartItems> items = cart.getItems();

        if(items.size()<=0){
            throw new BadRequestException("Item is not avilable in cart");
        }

        Order order = Order.builder()
                .billingAddress(orderDto.getBillingAddress())
                .billingPhone(orderDto.getBillingPhone())
                .billingName(orderDto.getBillingName())
                .orderedDate(new Date())
                .orderStatus(orderDto.getOrderStatus())
                .deliveredDate(orderDto.getDeliveredDate())
                .paymentStatus(orderDto.getPaymentStatus())
                .user(user)
                .build();

       // AtomicReference<Long> orderAmount = new AtomicReference<>(0);
        AtomicReference<Long> orderAmount = new AtomicReference<Long>(0L);
        List<OrderItem> orderItems = items.stream().map(cartItems -> {
            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItems.getQuantity())
                    .product(cartItems.getProduct())
                    .totalPrice(cartItems.getQuantity() * cartItems.getProduct().getPrice())
                    .order(order)
                    .build();

            orderAmount.set(orderAmount.get()+orderItem.getTotalPrice());
            return orderItem;
        }).collect(Collectors.toList());

        order.setOrderItem(orderItems);
        order.setOrderAmount(orderAmount.get());

        cart.getItems().clear();
        cartRepo.save(cart);
        Order save = orderRepo.save(order);


        return modelMapper.map(save,OrderDto.class);
    }

    @Override
    public void removeOrder(long orderId) {

        Order order = orderRepo.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("Order is not found"));
        orderRepo.delete(order);
    }

    @Override
    public List<OrderDto> getOrdersofUser(long userId) {
        User user = userRpo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found"));
        List<Order> orders = orderRepo.findByUser(user);
        List<OrderDto> stream = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return stream;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber,int pageSize,String SortBy,String SortDir) {
        Sort sort = (SortDir.equalsIgnoreCase("desc")) ? (Sort.by(SortBy).descending()) :(Sort.by(SortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Order> all = this.orderRepo.findAll(pageable);
        PageableResponse<OrderDto> pageableResponse = CustomPagenation.getPageableResponse(all, OrderDto.class);
        return pageableResponse;
    }
}

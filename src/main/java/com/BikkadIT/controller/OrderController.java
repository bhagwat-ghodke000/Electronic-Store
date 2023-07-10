package com.BikkadIT.controller;

import com.BikkadIT.configuration.AppConstant;
import com.BikkadIT.dtos.OrderDto;
import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.dtos.ProductDto;
import com.BikkadIT.services.Impl.OrderImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderImpl orderImpl;
    @PostMapping("/{userId}/cartId/{cartId}")
    ResponseEntity<OrderDto> CreateOrder(@Valid @RequestBody OrderDto orderDto, @PathVariable long userId, @PathVariable long cartId){
        OrderDto creteOrder = this.orderImpl.creteOrder(orderDto, userId, cartId);
        return new ResponseEntity<OrderDto>(creteOrder, HttpStatus.CREATED);
    }

    @DeleteMapping("/{orderId}")
    ResponseEntity<String> RemoveOrder(@PathVariable long orderId){
        this.orderImpl.removeOrder(orderId);
        return new ResponseEntity<>(AppConstant.ORDER_DELETE_MESSAGE,HttpStatus.OK);
    }

    @GetMapping("/userId/{userId}")
    ResponseEntity<List<OrderDto>> getOrdersOfUser(@PathVariable long userId){
        List<OrderDto> ordersofUser = this.orderImpl.getOrdersofUser(userId);
        return new ResponseEntity<List<OrderDto>>(ordersofUser,HttpStatus.OK);
    }

    @GetMapping("/")
    ResponseEntity<PageableResponse<OrderDto>> getAllProduct(
            @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value = "PageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSIze,
            @RequestParam(value = "sortBy",defaultValue ="orderedDate",required = false ) String SortBy,
            @RequestParam(value = "SortDir",defaultValue = "DSC",required = false) String SortDir
    ){
        PageableResponse<OrderDto> allOrders = this.orderImpl.getOrders(pageNumber, pageSIze, SortBy, SortDir);
        return new ResponseEntity<PageableResponse<OrderDto>>(allOrders,HttpStatus.OK);
    }

}

package com.BikkadIT.services;

import com.BikkadIT.dtos.OrderDto;
import com.BikkadIT.dtos.PageableResponse;

import java.util.List;

public interface OrderI {

    OrderDto creteOrder(OrderDto orderDto,long userId,long cartId);

    void removeOrder(long orderId);

    List<OrderDto> getOrdersofUser(long userId);

    PageableResponse<OrderDto> getOrders(int pageNumber,int pageSize,String SortBy,String SortDir);
}

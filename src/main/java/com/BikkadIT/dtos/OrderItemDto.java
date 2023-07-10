package com.BikkadIT.dtos;

import com.BikkadIT.entity.Order;
import com.BikkadIT.entity.Product;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class OrderItemDto {

    private int quantity;
    private long totalPrice;

    private Product product;
    private Order order;
}

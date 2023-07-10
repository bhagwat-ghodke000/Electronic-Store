package com.BikkadIT.dtos;

import com.BikkadIT.entity.Product;
import lombok.*;

import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    private long cartItemId;
    private Product product;
    private int quantity;
    private int totalPrice;
}

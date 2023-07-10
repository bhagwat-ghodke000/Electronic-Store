package com.BikkadIT.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddItemToCartRequest {

    private long productId;

    private int quantity;
}

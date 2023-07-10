package com.BikkadIT.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name ="cart_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItems {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartItemId;
    @OneToOne
    @JoinColumn(name ="product_id")
    private Product product;

    private int quantity;

    private int totalPrice;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="cart_id")
    private Cart cart;
}

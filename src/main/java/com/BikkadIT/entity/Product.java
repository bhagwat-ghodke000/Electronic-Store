package com.BikkadIT.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "products")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long ProductId;

    private String title;

    @Column(length = 1000)
    private String decription;

    private int price;

    private int quantity;

    @Column(columnDefinition = "Date")
    private Date addDate;

    private boolean live;

    private boolean stock;

    private String imageName;
    @ManyToOne
    private Category category;


}

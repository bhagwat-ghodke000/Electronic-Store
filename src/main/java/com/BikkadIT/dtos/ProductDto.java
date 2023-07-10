package com.BikkadIT.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDto {

    private Long ProductId;

    @NotNull(message = "Please insert title it is required")
    private String title;

    @Column(length = 1000)
    private String decription;

    @NotNull(message = "Please insert product price")
    private int price;

    private int quantity;

    @Column(columnDefinition = "Date")
    private Date addDate;

    private boolean live;

    private boolean stock;

    private String imageName;
}

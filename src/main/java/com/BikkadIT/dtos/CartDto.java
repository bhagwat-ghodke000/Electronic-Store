package com.BikkadIT.dtos;

import com.BikkadIT.entity.CartItems;
import com.BikkadIT.entity.User;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
    private long cartId;
    private Date createdAt;
    private User user;
    private List<CartItems> items=new ArrayList<>();
}

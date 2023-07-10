package com.BikkadIT.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name ="carts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Cart {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private long cartId;

    private Date createdAt;

    @OneToOne
    private User user;


    @OneToMany(mappedBy ="cart",cascade = CascadeType.ALL,fetch = FetchType.EAGER,orphanRemoval = true)
    @JsonIgnore
    private List<CartItems> items=new ArrayList<>();
}

package com.BikkadIT.repository;

import com.BikkadIT.entity.CartItems;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartItemRepo extends JpaRepository<CartItems,Long> {

}

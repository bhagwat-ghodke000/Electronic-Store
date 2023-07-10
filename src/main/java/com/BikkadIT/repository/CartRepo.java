package com.BikkadIT.repository;

import com.BikkadIT.entity.Cart;
import com.BikkadIT.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepo extends JpaRepository<Cart,Long> {


    Optional<Cart> findByUser(User user);
}

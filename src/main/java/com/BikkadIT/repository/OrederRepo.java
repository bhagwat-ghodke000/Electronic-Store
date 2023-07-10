package com.BikkadIT.repository;

import com.BikkadIT.entity.Order;
import com.BikkadIT.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrederRepo extends JpaRepository<Order,Long> {

    List<Order> findByUser(User user);
}

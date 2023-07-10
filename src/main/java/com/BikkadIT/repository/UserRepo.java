package com.BikkadIT.repository;

import com.BikkadIT.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepo extends JpaRepository<User,Long> {

    User findByEmail(String email);

    List<User> findByNameContaining(String keywords);

}

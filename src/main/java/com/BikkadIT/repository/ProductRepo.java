package com.BikkadIT.repository;

import com.BikkadIT.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product,Long> {

   Page<Product> findByLiveTrue(Pageable pageable);

    Page<Product> findByTitleContaining(String key,Pageable pageable);


}

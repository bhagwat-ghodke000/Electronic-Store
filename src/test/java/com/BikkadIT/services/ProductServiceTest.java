package com.BikkadIT.services;

import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.dtos.ProductDto;
import com.BikkadIT.entity.Product;
import com.BikkadIT.repository.ProductRepo;
import com.BikkadIT.services.Impl.ProductImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class ProductServiceTest {
    @MockBean
    private ProductRepo productRepo;
    @Autowired
    private ProductImpl productImpl;
    @Autowired
    private ModelMapper modelMapper;

    Product product;

    long productId=0;

    @BeforeEach
    public void init(){
        product = Product.builder().title("Mobile")
                .decription("This is Samsung 5G Mobile")
                .stock(true)
                .live(true)
                .price(20000)
                .quantity(8)
                .build();

        productId=10;
    }
    @Test
    public void addProductTest(){

        Mockito.when(this.productRepo.save(Mockito.any())).thenReturn(product);
        ProductDto productDto = this.modelMapper.map(product, ProductDto.class);
        ProductDto productDto1 = this.productImpl.addProduct(productDto);
        Assertions.assertNotNull(productDto1);
    }

    @Test
    public void getProductTest(){
        Mockito.when(this.productRepo.findById(productId)).thenReturn(Optional.of(product));
        ProductDto product1 = this.productImpl.getProduct(productId);
        Assertions.assertNotNull(product1);
    }

    @Test
    public void updateProductTest(){

      ProductDto  product1 = ProductDto.builder().title("Headphone")
                .decription("This is Samsung 5G Mobile")
                .stock(true)
                .live(true)
                .price(20000)
                .quantity(8)
                .build();

      Mockito.when(this.productRepo.findById(productId)).thenReturn(Optional.of(product));
      Mockito.when(this.productRepo.save(Mockito.any())).thenReturn(product);

        ProductDto productDto = this.productImpl.updateProduct(product1, productId);
        Assertions.assertEquals(product1.getTitle(),productDto.getTitle());
    }

    @Test
    public void deleteProductTest(){
        Mockito.when(this.productRepo.findById(productId)).thenReturn(Optional.of(product));
        this.productImpl.deleteProduct(productId);
        Mockito.verify(productRepo,Mockito.times(1)).delete(product);
    }

    @Test
    public void getAllProductTest(){

        Product product1 = Product.builder().title("Mobile")
                .decription("This is Samsung 5G Mobile")
                .stock(true)
                .live(true)
                .price(20000)
                .quantity(8)
                .build();

        Product product2 = Product.builder().title("Mobile")
                .decription("This is Redmi 5G Mobile")
                .stock(true)
                .live(true)
                .price(15000)
                .quantity(10)
                .build();

        List<Product> list = new ArrayList<>();
        list.add(product1);
        list.add(product2);
        list.add(product);

        Page<Product> products = new PageImpl<>(list);

        Mockito.when(productRepo.findAll((Pageable) Mockito.any())).thenReturn(products);
        PageableResponse<ProductDto> allProduct = productImpl.getAllProduct(1, 2, "title", "asc");
        Assertions.assertEquals(3,allProduct.getContent().size());

    }

    @Test
    public void getAllLiveProductTest(){
        Product product1 = Product.builder().title("Mobile")
                .decription("This is Samsung 5G Mobile")
                .stock(true)
                .live(true)
                .price(20000)
                .quantity(8)
                .build();

        Product product2 = Product.builder().title("Mobile")
                .decription("This is Redmi 5G Mobile")
                .stock(true)
                .live(true)
                .price(15000)
                .quantity(10)
                .build();

        List<Product> list = new ArrayList<>();
        list.add(product1);
        list.add(product2);
        list.add(product);

        Page<Product> products = new PageImpl<>(list);

        Mockito.when(productRepo.findByLiveTrue((Pageable) Mockito.any())).thenReturn(products);
        PageableResponse<ProductDto> allProduct = productImpl.getAllLiveProduct(1, 2, "title", "asc");
        Assertions.assertEquals(3,allProduct.getContent().size());

    }

    public void uploadProductImage(){

    }


}

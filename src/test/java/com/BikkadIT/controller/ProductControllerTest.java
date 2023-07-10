package com.BikkadIT.controller;

import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.dtos.ProductDto;
import com.BikkadIT.entity.Product;
import com.BikkadIT.services.Impl.ProductImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class ProductControllerTest {
    @MockBean
    private ProductImpl productImpl;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;

    private Product product;

    long productId=0;
    @BeforeEach
    public void init(){
        product=Product.builder().title("Mobile")
                .price(12000)
                .live(true)
                .stock(true)
                .decription("This is Samsung 5G Mobile")
                .quantity(13)
                .imageName("abc.png")
                .build();

        productId=23;
    }
    @Test
    public void addProductTest() throws Exception {
        ProductDto map = this.modelMapper.map(product, ProductDto.class);
        Mockito.when(this.productImpl.addProduct(Mockito.any())).thenReturn(map);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/product/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertobjectToJsonString(product))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    public void getProductTest() throws Exception {
        ProductDto map = this.modelMapper.map(product, ProductDto.class);
        Mockito.when(this.productImpl.getProduct(productId)).thenReturn(map);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/"+productId)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(convertobjectToJsonString(product))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateProductTest() throws Exception {
        ProductDto map = this.modelMapper.map(product, ProductDto.class);
        Mockito.when(this.productImpl.updateProduct(Mockito.any(),Mockito.anyInt())).thenReturn(map);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/product/"+productId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertobjectToJsonString(product))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void getAllProductTest() throws Exception {

        ProductDto product1 = ProductDto.builder().title("Mobile")
                .decription("This is Samsung 5G Mobile")
                .stock(true)
                .live(true)
                .price(20000)
                .quantity(8)
                .build();

        ProductDto product2 = ProductDto.builder().title("Mobile")
                .decription("This is Redmi 5G Mobile")
                .stock(true)
                .live(true)
                .price(15000)
                .quantity(10)
                .build();

        List<ProductDto> list = new ArrayList<>();
        list.add(product1);
        list.add(product2);

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setTotalElements(2);
        pageableResponse.setContent(list);
        pageableResponse.setTotalPages(1);
        pageableResponse.setPageSize(1);
        pageableResponse.setLastPage(true);

        Mockito.when(this.productImpl.getAllProduct(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getAllLiveProductTest() throws Exception {

        ProductDto product1 = ProductDto.builder().title("Mobile")
                .decription("This is Samsung 5G Mobile")
                .stock(true)
                .live(true)
                .price(20000)
                .quantity(8)
                .build();

        ProductDto product2 = ProductDto.builder().title("Mobile")
                .decription("This is Redmi 5G Mobile")
                .stock(true)
                .live(true)
                .price(15000)
                .quantity(10)
                .build();

        List<ProductDto> list = new ArrayList<>();
        list.add(product1);
        list.add(product2);

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setTotalElements(2);
        pageableResponse.setContent(list);
        pageableResponse.setTotalPages(1);
        pageableResponse.setPageSize(1);
        pageableResponse.setLastPage(true);

        Mockito.when(this.productImpl.getAllLiveProduct(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/getAllLiveProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getProductByNameContainingTest() throws Exception {

        ProductDto product1 = ProductDto.builder().title("Mobile")
                .decription("This is Samsung 5G Mobile")
                .stock(true)
                .live(true)
                .price(20000)
                .quantity(8)
                .build();

        ProductDto product2 = ProductDto.builder().title("Mobile")
                .decription("This is Redmi 5G Mobile")
                .stock(true)
                .live(true)
                .price(15000)
                .quantity(10)
                .build();

        List<ProductDto> list = new ArrayList<>();
        list.add(product1);
        list.add(product2);

        PageableResponse<ProductDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setTotalElements(2);
        pageableResponse.setContent(list);
        pageableResponse.setTotalPages(1);
        pageableResponse.setPageSize(1);
        pageableResponse.setLastPage(true);

        String Key="bha";
        Mockito.when(this.productImpl.searchProductByName(Mockito.anyString(),Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/product/keyword/"+Key)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }



    private String convertobjectToJsonString(Object product) {
        try {
            return new ObjectMapper().writeValueAsString(product);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

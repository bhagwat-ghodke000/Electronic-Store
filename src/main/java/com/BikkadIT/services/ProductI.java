package com.BikkadIT.services;

import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.dtos.ProductDto;

import java.util.List;

public interface ProductI {

    ProductDto addProduct(ProductDto productDto);

    PageableResponse<ProductDto> getAllProduct(int pageNumber, int pageSize, String SortBy, String SortDir);

    PageableResponse<ProductDto> getAllLiveProduct(int pageNumber,int pageSize,String SortBy,String SortDir);

    PageableResponse<ProductDto> searchProductByName(String key,int pageNumber,int pageSize,String SortBy,String SortDir);

    ProductDto getProduct(long productId);

    ProductDto updateProduct(ProductDto productDto,long productId);

    void  deleteProduct(long productId);
}

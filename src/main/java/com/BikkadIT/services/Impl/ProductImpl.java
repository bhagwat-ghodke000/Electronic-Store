package com.BikkadIT.services.Impl;

import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.dtos.ProductDto;
import com.BikkadIT.entity.Product;
import com.BikkadIT.exception.ResourceNotFoundException;
import com.BikkadIT.helper.CustomPagenation;
import com.BikkadIT.repository.ProductRepo;
import com.BikkadIT.services.ProductI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductImpl implements ProductI {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ProductDto addProduct(ProductDto productDto) {
        Product product = this.modelMapper.map(productDto, Product.class);
        Product save = this.productRepo.save(product);
        ProductDto productDto1 = this.modelMapper.map(save, ProductDto.class);
        return productDto1;
    }

    @Override
    public PageableResponse<ProductDto> getAllProduct(int pageNumber,int pageSize,String SortBy,String SortDir) {
        Sort sort = (SortDir.equalsIgnoreCase("desc")) ? (Sort.by(SortBy).descending()) :(Sort.by(SortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> all = this.productRepo.findAll(pageable);
        PageableResponse<ProductDto> pageableResponse = CustomPagenation.getPageableResponse(all, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> getAllLiveProduct(int pageNumber,int pageSize,String SortBy,String SortDir) {
        Sort sort = (SortDir.equalsIgnoreCase("desc")) ? (Sort.by(SortBy).descending()) :(Sort.by(SortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> all = this.productRepo.findByLiveTrue(pageable);
        PageableResponse<ProductDto> pageableResponse = CustomPagenation.getPageableResponse(all, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public PageableResponse<ProductDto> searchProductByName(String key,int pageNumber,int pageSize,String SortBy,String SortDir) {
        Sort sort = (SortDir.equalsIgnoreCase("desc")) ? (Sort.by(SortBy).descending()) :(Sort.by(SortBy).ascending());
        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<Product> all = this.productRepo.findByTitleContaining(key,pageable);
        PageableResponse<ProductDto> pageableResponse = CustomPagenation.getPageableResponse(all, ProductDto.class);

        return pageableResponse;
    }

    @Override
    public ProductDto getProduct(long productId) {
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("this Product is Not Avilable"));
        ProductDto productDto = this.modelMapper.map(product, ProductDto.class);
        return productDto;
    }

    @Override
    public ProductDto updateProduct(ProductDto productDto, long productId) {
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("this Product is Not Avilable"));
        product.setTitle(productDto.getTitle());
        product.setDecription(productDto.getDecription());
        product.setLive(productDto.isLive());
        product.setStock(productDto.isStock());
        product.setPrice(productDto.getPrice());
        product.setQuantity(productDto.getQuantity());

        Product save = this.productRepo.save(product);
        ProductDto map = this.modelMapper.map(save, ProductDto.class);

        return map;
    }

    @Override
    public void deleteProduct(long productId) {
        Product product = this.productRepo.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product is not Avilable"));
        this.productRepo.delete(product);
    }
}

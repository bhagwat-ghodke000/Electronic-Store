package com.BikkadIT.controller;

import com.BikkadIT.configuration.AppConstant;
import com.BikkadIT.dtos.ImageResponse;
import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.dtos.ProductDto;
import com.BikkadIT.dtos.UserDto;
import com.BikkadIT.services.Impl.FileImpl;
import com.BikkadIT.services.Impl.ProductImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;


@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductImpl product;
    @Autowired
    private FileImpl fileImpl;
    @Value("${product.profile.image.path}")
    private String filePath;


    @PostMapping("/")
    ResponseEntity<ProductDto> addProduct(@RequestBody ProductDto productDto){
        ProductDto productDto1 = this.product.addProduct(productDto);
        return new ResponseEntity<ProductDto>(productDto1, HttpStatus.CREATED);
    }

    @GetMapping("/{productId}")
    ResponseEntity<ProductDto> getProduct(@PathVariable long productId){
        ProductDto product1 = this.product.getProduct(productId);
        return new ResponseEntity<ProductDto>(product1,HttpStatus.OK);
    }

    @PutMapping("/{productId}")
    ResponseEntity<ProductDto> updateProduct(@PathVariable long productId,@RequestBody ProductDto productDto){
        ProductDto productDto1 = this.product.updateProduct(productDto, productId);
        return new ResponseEntity<ProductDto>(productDto1,HttpStatus.OK);
    }

    @DeleteMapping("/{productId}")
    ResponseEntity<String> deleteProduct(@PathVariable long productId){
        this.product.deleteProduct(productId);
        return new ResponseEntity<>(AppConstant.PRODUCT_DELETE_MESSAGE,HttpStatus.OK);
    }

    @GetMapping("/")
    ResponseEntity<PageableResponse<ProductDto>> getAllProduct(
            @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageNumber,
            @RequestParam(value = "PageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSIze,
            @RequestParam(value = "sortBy",defaultValue ="title",required = false ) String SortBy,
            @RequestParam(value = "SortDir",defaultValue = "ASC",required = false) String SortDir
    ){
        PageableResponse<ProductDto> allProduct = this.product.getAllProduct(pageNumber, pageSIze, SortBy, SortDir);
        return new ResponseEntity<PageableResponse<ProductDto>>(allProduct,HttpStatus.OK);
    }
    @GetMapping("/getAllLiveProduct")
    ResponseEntity<PageableResponse<ProductDto>> getAllLiveProduct(
            @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value = "PageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSIze,
            @RequestParam(value = "sortBy",defaultValue ="title",required = false ) String SortBy,
            @RequestParam(value = "SortDir",defaultValue = "ASC",required = false) String SortDir
    ){
        PageableResponse<ProductDto> allProduct = this.product.getAllLiveProduct(pageNumber, pageSIze, SortBy, SortDir);
        return new ResponseEntity<PageableResponse<ProductDto>>(allProduct,HttpStatus.OK);
    }

    @GetMapping("/keyword/{key}")
    ResponseEntity<PageableResponse<ProductDto>> getSearchByTitleContainingProduct(
            @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value = "PageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSIze,
            @RequestParam(value = "sortBy",defaultValue ="title",required = false ) String SortBy,
            @RequestParam(value = "SortDir",defaultValue = "ASC",required = false) String SortDir,
            @PathVariable String key
    ){
        PageableResponse<ProductDto> allProduct = this.product.searchProductByName(key,pageNumber, pageSIze, SortBy, SortDir);
        return new ResponseEntity<PageableResponse<ProductDto>>(allProduct,HttpStatus.OK);
    }

    @PostMapping("/image/{productId}")
    ResponseEntity<ImageResponse> uploadProductImage(@RequestParam("productImage")MultipartFile image, @PathVariable long productId) throws IOException {
        String file = this.fileImpl.uploadFile(image, filePath);
        ProductDto product1 = this.product.getProduct(productId);
        product1.setImageName(file);
        ProductDto productDto = this.product.updateProduct(product1, productId);
        ImageResponse imageIsUpload = ImageResponse.builder().imageName(file)
                .message("Image is Upload")
                .success(true)
                .status(HttpStatus.OK).build();
        return new ResponseEntity<ImageResponse>(imageIsUpload,HttpStatus.OK);
    }

    @GetMapping("image/{productId}")
    public void serveUserImage(@PathVariable long productId, HttpServletResponse response) throws IOException {
        ProductDto product1 = product.getProduct(productId);

        InputStream resource = this.fileImpl.getResource(filePath, product1.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());

    }
}

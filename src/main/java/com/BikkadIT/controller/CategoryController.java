package com.BikkadIT.controller;

import com.BikkadIT.configuration.AppConstant;
import com.BikkadIT.dtos.CategoryDto;
import com.BikkadIT.dtos.ImageResponse;
import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.services.Impl.CategoryImpl;
import com.BikkadIT.services.Impl.FileImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryImpl category;

    @Autowired
    private FileImpl file;

    @Value("$images/categoryCover/")
    private String imageUploadPath;

    @PostMapping("/")
    ResponseEntity<CategoryDto> addCategory(@RequestBody CategoryDto categoryDto){

        CategoryDto categoryDto1 = this.category.addCategory(categoryDto);
        return new ResponseEntity<CategoryDto>(categoryDto1, HttpStatus.CREATED);
    }
@GetMapping("/{categoryId}")
    ResponseEntity<CategoryDto> getCategory(@PathVariable long categoryId){
        CategoryDto category1 = this.category.getCategory(categoryId);
        return new ResponseEntity<CategoryDto>(category1,HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    ResponseEntity<CategoryDto> updateCategory(@PathVariable long categoryId,@RequestBody CategoryDto categoryDto){
        CategoryDto categoryDto1 = this.category.updateCategory(categoryDto, categoryId);
        return new ResponseEntity<CategoryDto>(categoryDto1,HttpStatus.CREATED);
    }

    @DeleteMapping("/{categoryId}")
    ResponseEntity<String> deleteCategory(@PathVariable long categoryId){
        this.category.deleteCategory(categoryId);
        return new ResponseEntity<>(AppConstant.CATEGORY_DELETE_MESSAGE,HttpStatus.OK);
    }

    @GetMapping("/")
    ResponseEntity<PageableResponse<CategoryDto>> getAllCategories(
            @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
            @RequestParam(value = "PageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSIze,
            @RequestParam(value = "sortBy",defaultValue ="title",required = false ) String SortBy,
            @RequestParam(value = "SortDir",defaultValue = "ASC",required = false) String SortDir
    ){
        PageableResponse<CategoryDto> allCategory = this.category.getAllCategory(pageNumber, pageSIze, SortBy, SortDir);
        return new ResponseEntity<PageableResponse<CategoryDto>>(allCategory,HttpStatus.OK);
    }

    @PostMapping("/categoryId/{categoryId}")
    ResponseEntity<ImageResponse> uploadCategoryCoverImage(@RequestParam("categoryImage")MultipartFile image,@PathVariable long categoryId) throws IOException {
        String s = this.file.uploadFile(image, imageUploadPath);
        CategoryDto category1 = this.category.getCategory(categoryId);
        category1.setCoverImage(s);
        this.category.updateCategory(category1,categoryId);
        ImageResponse imageUploadSuccessfully = ImageResponse.builder().imageName(s).status(HttpStatus.CREATED).success(true).message("Image Upload Successfully").build();
        return new ResponseEntity<ImageResponse>(imageUploadSuccessfully,HttpStatus.CREATED);
    }



}

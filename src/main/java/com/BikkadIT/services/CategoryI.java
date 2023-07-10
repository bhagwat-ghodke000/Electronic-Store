package com.BikkadIT.services;

import com.BikkadIT.dtos.CategoryDto;
import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.dtos.UserDto;

public interface CategoryI {

    CategoryDto addCategory(CategoryDto categoryDto);

    CategoryDto getCategory(long categoryId);

    CategoryDto updateCategory(CategoryDto categoryDto,long categoryId);

    PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String SortBy, String SortDir);

    void deleteCategory(long categoryId);
}

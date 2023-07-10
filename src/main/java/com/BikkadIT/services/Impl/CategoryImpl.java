package com.BikkadIT.services.Impl;

import com.BikkadIT.dtos.CategoryDto;
import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.dtos.UserDto;
import com.BikkadIT.entity.Category;
import com.BikkadIT.exception.ResourceNotFoundException;
import com.BikkadIT.helper.CustomPagenation;
import com.BikkadIT.repository.CategoryRepo;
import com.BikkadIT.services.CategoryI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class CategoryImpl implements CategoryI {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CategoryRepo categoryRepo;
    @Override
    public CategoryDto addCategory(CategoryDto categoryDto) {
        Category category = this.modelMapper.map(categoryDto, Category.class);
        Category category1 = this.categoryRepo.save(category);
        CategoryDto categoryDto1 = this.modelMapper.map(category1, CategoryDto.class);
        return categoryDto1;
    }

    @Override
    public CategoryDto getCategory(long categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category is not avilable for this id"));
        CategoryDto map = this.modelMapper.map(category, CategoryDto.class);
        return map;
    }

    @Override
    public CategoryDto updateCategory(CategoryDto categoryDto, long categoryId) {
        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("This Category is not avilable for id"));
        category.setDescription(categoryDto.getDescription());
        category.setTitle(categoryDto.getTitle());
        category.setCoverImage(categoryDto.getCoverImage());
        Category save = this.categoryRepo.save(category);
        CategoryDto map = this.modelMapper.map(save, CategoryDto.class);
        return map;
    }

    @Override
    public PageableResponse<CategoryDto> getAllCategory(int pageNumber, int pageSize, String SortBy, String SortDir) {
        Sort sort = (SortDir.equalsIgnoreCase("desc")) ? (Sort.by(SortBy).descending()) :(Sort.by(SortBy).ascending());
        PageRequest pageRequest = PageRequest.of(pageNumber, pageSize,sort);
        Page<Category> all = this.categoryRepo.findAll(pageRequest);
        PageableResponse<CategoryDto> pageableResponse = CustomPagenation.getPageableResponse(all, CategoryDto.class);
        return pageableResponse;
    }

    @Override
    public void deleteCategory(long categoryId) {

        Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("This Category is not Avilable"));
        this.categoryRepo.delete(category);

    }
}

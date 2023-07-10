package com.BikkadIT.services;

import com.BikkadIT.controller.UserControllerTest;
import com.BikkadIT.dtos.CategoryDto;
import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.entity.Category;
import com.BikkadIT.repository.CategoryRepo;
import com.BikkadIT.services.Impl.CategoryImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class CategoryServiceTest {
    @Autowired
    private CategoryImpl categoryImpl;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserControllerTest userController;
    @MockBean
    private CategoryRepo categoryRepo;

    Category category;

    long categoryId=0;
    @BeforeEach
    public void init(){
        category = Category.builder().title("Samsung mombile")
                .description("This is 5G Mobile")
                .coverImage("mobile.png")
                .build();

        categoryId=10;

  }

  @Test
  public void createCategoryTest(){
      Mockito.when(this.categoryRepo.save(Mockito.any())).thenReturn(category);

      CategoryDto map = this.modelMapper.map(category, CategoryDto.class);
      CategoryDto categoryDto = this.categoryImpl.addCategory(map);
      Assertions.assertEquals(category.getTitle(),categoryDto.getTitle());

    }

    @Test
    public void getCategoryTest(){
        Mockito.when(this.categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
        CategoryDto category1 = this.categoryImpl.getCategory(categoryId);
        CategoryDto categoryDto = this.modelMapper.map(category1, CategoryDto.class);
        Assertions.assertNotNull(categoryDto);
    }

    @Test
    public void getAllCategoryTest(){
        Category category1=Category.builder().title("Nokia")
                .coverImage("Nokia.png")
                .description("This mobile is 4G")
                .build();

        Category category2=Category.builder().title("Redmi")
                .coverImage("Redmi.png")
                .description("This is Redmi Mobile")
                .build();

        List<Category> list = new ArrayList<>();
        list.add(category);
        list.add(category1);
        list.add(category2);

        PageImpl<Category> categories = new PageImpl<>(list);
        Mockito.when(this.categoryRepo.findAll((Pageable) Mockito.any())).thenReturn(categories);
        PageableResponse<CategoryDto> allCategory = this.categoryImpl.getAllCategory(1, 2, "title", "asc");
        Assertions.assertEquals(3,allCategory.getContent().size());
    }

    @Test
    public void updateCategoryTest(){

        Category category1=Category.builder().title("Nokia")
                .coverImage("Nokia.png")
                .description("This mobile is 4G")
                .build();
        Mockito.when(this.categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
        Mockito.when(this.categoryRepo.save(Mockito.any())).thenReturn(category);
        CategoryDto categoryDto = this.modelMapper.map(category1, CategoryDto.class);
        CategoryDto categoryDto1 = this.categoryImpl.updateCategory(categoryDto, categoryId);
        Assertions.assertEquals(category.getTitle(),categoryDto.getTitle());
    }

    @Test
    public void deleteCategoryTest(){
        Mockito.when(this.categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
        this.categoryImpl.deleteCategory(categoryId);
        Mockito.verify(this.categoryRepo,Mockito.times(1)).delete(category);
    }
}

package com.BikkadIT.controller;

import com.BikkadIT.dtos.CategoryDto;
import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.dtos.UserDto;
import com.BikkadIT.entity.Category;
import com.BikkadIT.services.Impl.CategoryImpl;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {
    @MockBean
    private CategoryImpl categoryImpl;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mockMvc;

    private Category category;

    long categoryId=0;

    @BeforeEach
    public void init(){
        category = Category.builder().title("Mobile")
                .description("This is a unit")
                .coverImage("abc.png")
                .build();

        categoryId=13;


    }
    @Test
    public void addCategoryTest() throws Exception {

        //CategoryDto map = this.modelMapper.map(category, CategoryDto.class);
        CategoryDto map = this.modelMapper.map(category, CategoryDto.class);
        Mockito.when(this.categoryImpl.addCategory(Mockito.any())).thenReturn(map);
        this.mockMvc.perform(MockMvcRequestBuilders.post("/category/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertobjectToJsonString(category))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void getCategoryTest() throws Exception {
        CategoryDto map = this.modelMapper.map(category, CategoryDto.class);
        Mockito.when(this.categoryImpl.getCategory(categoryId)).thenReturn(map);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/category/"+categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertobjectToJsonString(category))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void updateCategoryTest() throws Exception {
        CategoryDto map = this.modelMapper.map(category, CategoryDto.class);
        Mockito.when(this.categoryImpl.updateCategory(map,categoryId)).thenReturn(map);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/category/"+categoryId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertobjectToJsonString(category))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isCreated());
    }
@Test
    public void getAllCategoryTest() throws Exception {
        CategoryDto category1=CategoryDto.builder().title("Nokia")
                .coverImage("Nokia.png")
                .description("This mobile is 4G")
                .build();

        CategoryDto category2=CategoryDto.builder().title("Redmi")
                .coverImage("Redmi.png")
                .description("This is Redmi Mobile")
                .build();

        List<CategoryDto> list = new ArrayList<>();
        list.add(category1);
        list.add(category2);

        PageableResponse<CategoryDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setPageSize(3);
        pageableResponse.setTotalPages(5);
        pageableResponse.setTotalElements(30);
        pageableResponse.setContent(list);
        pageableResponse.setLastPage(true);

        Mockito.when(this.categoryImpl.getAllCategory(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/category/")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    public void deleteCategory() throws Exception {
        CategoryDto map = this.modelMapper.map(category, CategoryDto.class);
        //Mockito.when(this.categoryImpl.deleteCategory(Mockito.anyInt()));
        this.mockMvc.perform(MockMvcRequestBuilders.delete("/category/"+categoryId)
                .contentType(MediaType.APPLICATION_JSON)

        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    private String convertobjectToJsonString(Object category) {
        try {
            return new ObjectMapper().writeValueAsString(category);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

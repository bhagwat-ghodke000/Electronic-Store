package com.BikkadIT.controller;

import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.dtos.UserDto;
import com.BikkadIT.entity.User;
import com.BikkadIT.services.Impl.userImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private userImpl userService;

    @Autowired
    private ModelMapper modelMapper;

    private User user;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserController userController;

    long userId=0;

    @BeforeEach
    public void init(){
        user = User.builder().name("bhagwat")
                .email("bg000@gmail.com")
                .about("This is first user")
                .gender("Male")
                .password("Bg@12345")
                .imageName("abc.png")
                .build();

        userId=10;
    }

    @Test
    public void createUserTest() throws Exception {
        UserDto map = this.modelMapper.map(user, UserDto.class);
        Mockito.when(this.userService.addUser(Mockito.any())).thenReturn(map);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/")
                .contentType(MediaType.APPLICATION_JSON)
                .contentType(convertobjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
@Test
    public void create(){
        UserDto map = this.modelMapper.map(user, UserDto.class);
        Mockito.when(this.userService.addUser(Mockito.any())).thenReturn(map);
        ResponseEntity<UserDto> user1 = this.userController.createUser(map);

        Assertions.assertEquals(HttpStatus.OK,user1.getStatusCode());
    }

    @Test
    public void updateUserTest() throws Exception {

        UserDto map = this.modelMapper.map(user, UserDto.class);

        Mockito.when(this.userService.updateUser(map,userId)).thenReturn(map);
        this.mockMvc.perform(MockMvcRequestBuilders.put("/user/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertobjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON)

        )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test

    public void getAllUserTest() throws Exception {

        UserDto userDto = UserDto.builder().name("Shivraj")
                .email("shiv000@gmail.com")
                .about("This is Updated user")
                .gender("Male")
                .imageName("abc.png")
                .build();

        UserDto userDto1 = UserDto.builder().name("Bhagwat")
                .email("bg000@gmail.com")
                .about("This is Second user")
                .gender("Male")
                .imageName("abc.png")
                .build();
        UserDto userDto2 = UserDto.builder().name("Datta")
                .email("datta000@gmail.com")
                .about("This is thired user")
                .gender("Male")
                .imageName("abc.png")
                .build();

        UserDto userDto3 = UserDto.builder().name("Swaraj")
                .email("swa00@gmail.com")
                .about("This is thired user")
                .gender("Male")
                .imageName("abc.png")
                .build();

        List<UserDto> list = new ArrayList<>();
        list.add(userDto);
        list.add(userDto1);
        list.add(userDto2);
        list.add(userDto3);

        PageableResponse<UserDto> pageableResponse = new PageableResponse<>();
        pageableResponse.setContent(list);
        pageableResponse.setLastPage(true);
        pageableResponse.setTotalPages(20);
        pageableResponse.setTotalElements(1000);
        pageableResponse.setPageSize(5);
        pageableResponse.setPageSize(2);
        Mockito.when(this.userService.getAllUser(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user")
                .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void getUserTest() throws Exception {
        UserDto map = this.modelMapper.map(user, UserDto.class);
        Mockito.when(this.userService.getUser(userId)).thenReturn(map);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(convertobjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk());
    }




    private String convertobjectToJsonString(Object user) {
        try {
            return new ObjectMapper().writeValueAsString(user);

        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}

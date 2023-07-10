package com.BikkadIT.services;

import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.dtos.UserDto;
import com.BikkadIT.entity.User;
import com.BikkadIT.repository.UserRepo;
import com.BikkadIT.services.Impl.userImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private userImpl userImpl;

    User user;

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
    public void createUserTest(){
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);
        UserDto userDto = userImpl.addUser(modelMapper.map(user, UserDto.class));
        System.out.println(userDto.getImageName());
        Assertions.assertNotNull(userDto);
    }
    @Test
    public void getUserTest(){

        Mockito.when(userRepo.findById(Mockito.any())).thenReturn(Optional.of(user));
        UserDto user1 = userImpl.getUser(userId);
        Assertions.assertEquals(user.getName(),user1.getName());
    }
    @Test
    public void getUserByEmailTest(){

        Mockito.when(userRepo.findByEmail(user.getEmail())).thenReturn(user);
        UserDto userDto = userImpl.getUserByEmail(user.getEmail());
        Assertions.assertEquals(user.getEmail(),userDto.getEmail());
    }

    @Test
    public void updateUserTest(){

        UserDto userDto = UserDto.builder().name("Shivraj")
                .email("shiv000@gmail.com")
                .about("This is Updated user")
                .gender("Male")
                .imageName("abc.png")
                .build();

        Mockito.when(userRepo.findById(Mockito.any())).thenReturn(Optional.of(user));
        Mockito.when(userRepo.save(Mockito.any())).thenReturn(user);

        UserDto userDto1 = userImpl.updateUser(userDto, userId);

        System.out.println(userDto1.getName());
        System.out.println(user.getName());

        Assertions.assertEquals(userDto.getName(),userDto1.getName(),"Data is not updated");

    }

    @Test
    public void deleteUserTest(){
        Mockito.when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        this.userImpl.deleteUser(userId);
        Mockito.verify(userRepo,Mockito.times(1)).delete(user);
    }

    @Test
    public void getAllUserTest(){
        User userDto = User.builder().name("Shivraj")
                .email("shiv000@gmail.com")
                .about("This is Updated user")
                .gender("Male")
                .imageName("abc.png")
                .build();

        User userDto1 = User.builder().name("Bhagwat")
                .email("bg000@gmail.com")
                .about("This is Second user")
                .gender("Male")
                .imageName("abc.png")
                .build();
        User userDto2 = User.builder().name("Datta")
                .email("datta000@gmail.com")
                .about("This is thired user")
                .gender("Male")
                .imageName("abc.png")
                .build();

        User userDto3 = User.builder().name("Swaraj")
                .email("swa00@gmail.com")
                .about("This is thired user")
                .gender("Male")
                .imageName("abc.png")
                .build();
        List<User> list = new ArrayList<>();
        list.add(userDto);
        list.add(userDto1);
        list.add(userDto2);
        list.add(userDto3);

        Page<User> users = new PageImpl<>(list);

        Mockito.when(userRepo.findAll((Pageable) Mockito.any())).thenReturn(users);

        PageableResponse<UserDto> allUser = userImpl.getAllUser(1, 2, "name", "asc");

        Assertions.assertEquals(4,allUser.getContent().size());
    }

    @Test
    public void searchByUser(){
        User userDto = User.builder().name("Shivraj")
                .email("shiv000@gmail.com")
                .about("This is Updated user")
                .gender("Male")
                .imageName("abc.png")
                .build();

        User userDto1 = User.builder().name("Bhagwat")
                .email("bg000@gmail.com")
                .about("This is Second user")
                .gender("Male")
                .imageName("abc.png")
                .build();
        User userDto2 = User.builder().name("Datta")
                .email("datta000@gmail.com")
                .about("This is thired user")
                .gender("Male")
                .imageName("abc.png")
                .build();

        User userDto3 = User.builder().name("Swaraj")
                .email("swa00@gmail.com")
                .about("This is thired user")
                .gender("Male")
                .imageName("abc.png")
                .build();
        List<User> list = new ArrayList<>();
        list.add(userDto);
        list.add(userDto1);
        list.add(userDto2);
        list.add(userDto3);

        String key="s";

        Mockito.when(userRepo.findByNameContaining(key)).thenReturn(list);
        List<UserDto> list1 = userImpl.searchUserByName(key);
        Assertions.assertEquals(4,list1.size(),"Size is not match");
    }


}

package com.BikkadIT.services.Impl;

import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.dtos.UserDto;
import com.BikkadIT.entity.Role;
import com.BikkadIT.entity.User;
import com.BikkadIT.exception.ResourceNotFoundException;
import com.BikkadIT.helper.CustomPagenation;
import com.BikkadIT.repository.RoleRepo;
import com.BikkadIT.repository.UserRepo;
import com.BikkadIT.services.UserI;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class userImpl implements UserI {
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private RoleRepo roleRepo;

    @Value("${normal.role.id}")
    private long normalId;
    @Override
    public UserDto addUser(UserDto user) {
        User map = this.modelMapper.map(user, User.class);
        map.setPassword(passwordEncoder.encode(user.getPassword()));
        Role role = this.roleRepo.findById(normalId).get();
        map.getRoles().add(role);
        User save = this.userRepo.save(map);
        UserDto userDto = this.modelMapper.map(save, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto getUser(long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
        UserDto userDto = this.modelMapper.map(user, UserDto.class);
        return userDto;
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber,int pageSize,String SortBy,String SortDir) {

        Sort sort = (SortDir.equalsIgnoreCase("desc")) ? (Sort.by(SortBy).descending()) :(Sort.by(SortBy).ascending());

        Pageable pageable = PageRequest.of(pageNumber,pageSize,sort);
        Page<User> user = this.userRepo.findAll(pageable);
        PageableResponse<UserDto> pageableResponse = CustomPagenation.getPageableResponse(user, UserDto.class);
        return pageableResponse;
    }

    @Override
    public UserDto updateUser(UserDto userDto, long userId) {
        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());
        User save = this.userRepo.save(user);
        UserDto userDto1 = this.modelMapper.map(save, UserDto.class);
        return userDto1;
    }

    @Override
    public void deleteUser(long userId) {

        User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User is not Avilable"));
        this.userRepo.delete(user);

    }

    @Override
    public UserDto getUserByEmail(String email) {

        User byEmail = this.userRepo.findByEmail(email);
        UserDto map = this.modelMapper.map(byEmail, UserDto.class);

        return map;
    }

    @Override
    public List<UserDto> searchUserByName(String username) {
        List<User> user = this.userRepo.findByNameContaining(username);
        List<UserDto> userDtos = user.stream().map(list -> this.modelMapper.map(list, UserDto.class)).collect(Collectors.toList());
        return userDtos;
    }
}

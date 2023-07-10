package com.BikkadIT.services;

import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.dtos.UserDto;

import java.util.List;

public interface UserI {

    UserDto addUser(UserDto user);

    UserDto getUser(long userId);

    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String SortBy, String SortDir);

    UserDto updateUser(UserDto userDto,long userId);

    void deleteUser(long userId);

    UserDto getUserByEmail(String email);

    List<UserDto> searchUserByName(String username);

}

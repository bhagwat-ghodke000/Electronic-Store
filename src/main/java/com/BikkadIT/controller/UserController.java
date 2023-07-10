package com.BikkadIT.controller;

import com.BikkadIT.configuration.AppConstant;
import com.BikkadIT.dtos.ImageResponse;
import com.BikkadIT.dtos.PageableResponse;
import com.BikkadIT.dtos.UserDto;
import com.BikkadIT.services.Impl.FileImpl;
import com.BikkadIT.services.Impl.userImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private userImpl user;
    @Autowired
    private FileImpl file;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;
    @PostMapping("/")
    ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){

        UserDto userDto1 = user.addUser(userDto);
        return new ResponseEntity<UserDto>(userDto1, HttpStatus.OK);

    }
@GetMapping("/{userId}")
    ResponseEntity<UserDto> getUser(@PathVariable long userId){
        UserDto userDto = user.getUser(userId);
        return new ResponseEntity<UserDto>(userDto,HttpStatus.OK);
    }
    @PreAuthorize("hasRole('ADMIN')")
@GetMapping
    ResponseEntity<PageableResponse<UserDto>> getAllUser(
        @RequestParam(value = "pageNumber",defaultValue = AppConstant.PAGE_NUMBER,required = false) int pageNumber,
        @RequestParam(value = "PageSize",defaultValue = AppConstant.PAGE_SIZE,required = false) int pageSIze,
        @RequestParam(value = "sortBy",defaultValue ="name",required = false ) String SortBy,
        @RequestParam(value = "SortDir",defaultValue = "ASC",required = false) String SortDir
){
    PageableResponse<UserDto> allUser = user.getAllUser(pageNumber, pageSIze, SortBy, SortDir);
    return new ResponseEntity<PageableResponse<UserDto>>(allUser,HttpStatus.OK);
    }
@PutMapping("/{userId}")
    ResponseEntity<UserDto> updateUser( @Valid @PathVariable long userId,@RequestBody UserDto userDto){

        UserDto userDto1 = user.updateUser(userDto, userId);
        return new ResponseEntity<>(userDto1,HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    ResponseEntity<UserDto> getUserByEmail(@PathVariable String email){
        UserDto userByEmail = user.getUserByEmail(email);
        return new ResponseEntity<UserDto>(userByEmail,HttpStatus.OK);
    }

    @GetMapping("/name/{name}")
    ResponseEntity<List<UserDto>> searchUserByName(@PathVariable String name){
        List<UserDto> userDtos = user.searchUserByName(name);
        return new ResponseEntity<List<UserDto>>(userDtos,HttpStatus.OK);
    }
@PostMapping("/image/{userId}")
    ResponseEntity<ImageResponse> uploadUserImage(@RequestParam("userImage")MultipartFile image,@PathVariable long userId) throws IOException {
    String imageName = this.file.uploadFile(image, imageUploadPath);
    UserDto user1 = user.getUser(userId);
    user1.setImageName(imageName);
    UserDto userDto = user.updateUser(user1, userId);
    ImageResponse imageUploadSuccessfully = ImageResponse.builder().imageName(imageName).status(HttpStatus.CREATED).success(true).message("Image Upload Successfully").build();
    return new ResponseEntity<ImageResponse>(imageUploadSuccessfully,HttpStatus.CREATED);
}
@GetMapping("image/{userId}")
public void serveUserImage(@PathVariable long userId, HttpServletResponse response) throws IOException {

    UserDto user1 = user.getUser(userId);

    InputStream resource = this.file.getResource(imageUploadPath, user1.getImageName());
   response.setContentType(MediaType.IMAGE_JPEG_VALUE);
    StreamUtils.copy(resource,response.getOutputStream());

}
}

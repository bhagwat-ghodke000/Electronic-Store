package com.BikkadIT.controller;

import com.BikkadIT.dtos.JwtRequest;
import com.BikkadIT.dtos.JwtResponse;
import com.BikkadIT.dtos.UserDto;
import com.BikkadIT.exception.BadRequestException;
import com.BikkadIT.security.JwtHelper;
import com.BikkadIT.services.Impl.userImpl;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private userImpl user;
    @Autowired
    private JwtHelper helper;

    public ResponseEntity<UserDto> getCurrentUser(Principal principal){
        String name = principal.getName();
        return new ResponseEntity<>(modelMapper.map(userDetailsService.loadUserByUsername(name),UserDto.class), HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request){
        this.doAuthenticate(request.getEmail(),request.getPassword());
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getEmail());
        UserDto userDto = modelMapper.map(userDetails, UserDto.class);
        String s = this.helper.generateToken(userDetails);
        JwtResponse jwtResponse = JwtResponse.builder()
                .jwtToken(s)
                .user(userDto)
                .build();

        return new ResponseEntity<JwtResponse>(jwtResponse,HttpStatus.OK);
    }

    private void doAuthenticate(String email, String password) {

        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(email,password);

        try {
            manager.authenticate(authentication);
        }catch (BadCredentialsException e){
            throw new BadRequestException("In Correct Email and Password Please Check");
        }
    }
}

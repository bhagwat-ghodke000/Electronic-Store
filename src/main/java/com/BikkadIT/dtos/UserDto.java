package com.BikkadIT.dtos;

import com.BikkadIT.entity.Role;
import com.BikkadIT.helper.ImageValid;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

    private Long userId;


    private String name;

   @NotNull @Email
    private String email;

@Size(min = 7,max = 15,message = "Please Enter Password")
    private String password;

    private String about;
@NotNull
    private String gender;
    @ImageValid
    private String imageName;

    private Set<RoleDto> roles=new HashSet<>();

}

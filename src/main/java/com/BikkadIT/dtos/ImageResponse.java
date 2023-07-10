package com.BikkadIT.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ImageResponse {

    private String imageName;

    private String message;

    private HttpStatus status;
    private boolean success;

}

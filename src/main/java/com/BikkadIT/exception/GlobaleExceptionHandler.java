package com.BikkadIT.exception;

import com.BikkadIT.dtos.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestControllerAdvice
public class GlobaleExceptionHandler {
@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponse> resourceNotFoundExceptionHandler(ResourceNotFoundException ex){
    ApiResponse build = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.NOT_FOUND).success(true).build();
    return new ResponseEntity<ApiResponse>(build,HttpStatus.NOT_FOUND);
}
@ExceptionHandler(MethodArgumentNotValidException.class)
public ResponseEntity<Map<String,Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
    List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
    Map<String,Object> response = new HashMap<>();
    allErrors.stream().forEach(objectError -> {
        String message = objectError.getDefaultMessage();
        String field = ((FieldError)objectError).getField();
        response.put(message,field);
    });
    return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
}

@ExceptionHandler(BadRequestException.class)
   public ResponseEntity<ApiResponse> badRequestException(BadRequestException ex){
       ApiResponse response = ApiResponse.builder().message(ex.getMessage()).status(HttpStatus.BAD_REQUEST).success(false).build();
       return new ResponseEntity<ApiResponse>(response,HttpStatus.BAD_REQUEST);
   }
}

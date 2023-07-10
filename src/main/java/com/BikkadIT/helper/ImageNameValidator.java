package com.BikkadIT.helper;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ImageNameValidator implements ConstraintValidator<ImageValid,String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value.isBlank()){
            return false;
        }else {
            return true;
        }
    }
}

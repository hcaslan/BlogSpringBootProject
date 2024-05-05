package org.hca.blogproject.utility.validator;

import org.hca.blogproject.utility.annotation.MinLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinLengthValidator implements ConstraintValidator<MinLength, String> {
    private int minLength;

    @Override
    public void initialize(MinLength constraintAnnotation) {
        this.minLength = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.length() >= minLength;
    }
}
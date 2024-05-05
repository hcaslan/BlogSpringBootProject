package org.hca.blogproject.utility.validator;

import org.hca.blogproject.utility.annotation.MaxLength;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @CustomValidator
 * Custom validation annotation
 */
public class MaxLengthValidator implements ConstraintValidator<MaxLength, String> {
    private int maxLength;

    @Override
    public void initialize(MaxLength constraintAnnotation) {
        this.maxLength = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return value == null || value.length() <= maxLength;
    }
}

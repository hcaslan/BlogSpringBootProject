package org.hca.blogproject.utility.annotation;

import org.hca.blogproject.utility.validator.MaxLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MaxLengthValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MaxLength {
    String message() default "Length must not exceed {value} characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value();
}
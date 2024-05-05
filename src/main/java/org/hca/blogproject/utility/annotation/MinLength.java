package org.hca.blogproject.utility.annotation;

import org.hca.blogproject.utility.validator.MinLengthValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @CustomAnnotation
 * Custom validation annotation
 */
@Documented
@Constraint(validatedBy = MinLengthValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface MinLength {
    String message() default "Length must be at least {value} characters";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value();
}
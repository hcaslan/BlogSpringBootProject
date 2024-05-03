package org.hca.blogproject.utility;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

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
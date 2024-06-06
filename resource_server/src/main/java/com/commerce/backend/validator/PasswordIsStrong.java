package com.commerce.backend.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PasswordMeterValidator.class)
@Documented
public @interface PasswordIsStrong {
    String message() default "Password must be at least 12 characters long, with mixed case letters, at least one digit and at least one symbol.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

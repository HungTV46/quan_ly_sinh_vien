package com.example.QuanLySinhVien.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {BirthDateValidator.class})
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface BirthDateConstrain {

    String message() default "Birth date is invalid";

    int min();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

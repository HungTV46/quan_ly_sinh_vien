package com.example.QuanLySinhVien.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class BirthDateValidator implements ConstraintValidator<BirthDateConstrain, LocalDate> {
    private int min;

    @Override
    public void initialize(BirthDateConstrain constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        min = constraintAnnotation.min();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        long age = ChronoUnit.YEARS.between(value, LocalDate.now());
        return age >= min;
    }
}

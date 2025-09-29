package ru.yandex.practicum.filmorate.validator;

import ru.yandex.practicum.filmorate.annotation.DateAfter;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAfterValidator implements ConstraintValidator<DateAfter, LocalDate> {
    private LocalDate targetDate;

    @Override
    public void initialize(DateAfter constraintAnnotation) {
        this.targetDate = LocalDate.parse(constraintAnnotation.date(), DateTimeFormatter.ISO_DATE);
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }

        return value.isAfter(targetDate);
    }
}
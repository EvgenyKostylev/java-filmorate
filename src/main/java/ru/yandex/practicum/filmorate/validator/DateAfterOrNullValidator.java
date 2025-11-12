package ru.yandex.practicum.filmorate.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import ru.yandex.practicum.filmorate.annotation.DateAfterOrNull;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAfterOrNullValidator implements ConstraintValidator<DateAfterOrNull, LocalDate> {
    private LocalDate targetDate;

    @Override
    public void initialize(DateAfterOrNull constraintAnnotation) {
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
package ru.yandex.practicum.filmorate.annotation;

import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validator.DateAfterValidator;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateAfterValidator.class)
public @interface DateAfter {
    String message() default "Дата должна быть после указанной даты";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String date();
}
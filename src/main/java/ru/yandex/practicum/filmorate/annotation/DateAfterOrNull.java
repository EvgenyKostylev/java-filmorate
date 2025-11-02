package ru.yandex.practicum.filmorate.annotation;

import jakarta.validation.Payload;
import ru.yandex.practicum.filmorate.validator.DateAfterOrNullValidator;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DateAfterOrNullValidator.class)
public @interface DateAfterOrNull {
    String message() default "Дата должна быть после указанной даты или 0";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String date();
}
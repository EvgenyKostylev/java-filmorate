package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class User {
    private int id;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    @Pattern(regexp = "^[^\\s]*$")
    private final String login;

    private String name;

    @PastOrPresent
    private final LocalDate birthday;
}
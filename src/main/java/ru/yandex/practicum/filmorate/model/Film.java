package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.Duration;
import java.time.LocalDate;

@Data
public class Film {
    private static final int MAX_DESCRIPTION_LENGTH = 200;
    private int id;
    @NotBlank
    private final String name;
    @Size(max = MAX_DESCRIPTION_LENGTH)
    private final String description;
    private final LocalDate releaseDate;
    private final Duration duration;
}
package ru.yandex.practicum.filmorate.dto.film;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.DateAfter;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.time.LocalDate;
import java.util.List;

@Data
public class NewFilmRequest {
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    private static final String MIN_RELEASE_DATE = "1895-12-28";

    @NotBlank
    private final String name;

    @Size(max = MAX_DESCRIPTION_LENGTH)
    private final String description;

    @DateAfter(date = MIN_RELEASE_DATE)
    private final LocalDate releaseDate;

    @Positive
    private final Long duration;

    @JsonProperty("mpa")
    private final Rating rating;

    private final List<Genre> genres;
}
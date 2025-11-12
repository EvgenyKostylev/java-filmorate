package ru.yandex.practicum.filmorate.dto.film;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.DateAfterOrNull;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.time.LocalDate;
import java.util.List;

@Data
public class UpdateFilmRequest {
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    private static final String MIN_RELEASE_DATE = "1895-12-28";

    @Positive
    private final Long id;

    @NotBlank
    private final String name;

    @Size(max = MAX_DESCRIPTION_LENGTH)
    private final String description;

    @DateAfterOrNull(date = MIN_RELEASE_DATE)
    private final LocalDate releaseDate;

    @PositiveOrZero
    private final Long duration;

    @JsonProperty("mpa")
    private final Rating rating;

    private final List<Genre> genres;

    public boolean hasName() {
        return name != null && !name.isEmpty();
    }

    public boolean hasDescription() {
        return description != null && !description.isEmpty();
    }

    public boolean hasReleaseDate() {
        return releaseDate != null;
    }

    public boolean hasDuration() {
        return duration > 0;
    }

    public boolean hasRating() {
        return rating != null;
    }

    public boolean hasGenres() {
        return genres != null;
    }
}
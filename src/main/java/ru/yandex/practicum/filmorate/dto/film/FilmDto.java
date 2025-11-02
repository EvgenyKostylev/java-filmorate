package ru.yandex.practicum.filmorate.dto.film;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.DateAfter;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
public class FilmDto {
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    private static final String MIN_RELEASE_DATE = "1895-12-28";

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long id;

    @NotBlank
    private String name;

    @Size(max = MAX_DESCRIPTION_LENGTH)
    private String description;

    @DateAfter(date = MIN_RELEASE_DATE)
    private LocalDate releaseDate;

    @Positive
    private Long duration;

    @JsonProperty("genres")
    private List<Genre> genre;

    @JsonProperty("mpa")
    private Rating rating;
}
package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Like {
    private Long id;

    @Positive
    private Long userId;

    @Positive
    private Long filmId;
}
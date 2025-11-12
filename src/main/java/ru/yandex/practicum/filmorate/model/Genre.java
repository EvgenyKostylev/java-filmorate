package ru.yandex.practicum.filmorate.model;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class Genre {
    private final Long id;

    private final String name;

    public static List<Genre> parseGenresFromString(String genresStr) {
        if (genresStr == null || genresStr.isBlank()) {
            return new ArrayList<>();
        }

        return Arrays.stream(genresStr.split(","))
                .map(pair -> pair.split(":", 2))
                .filter(parts -> parts.length == 2)
                .map(parts -> Genre.builder()
                        .id(Long.parseLong(parts[0]))
                        .name(parts[1])
                        .build())
                .collect(Collectors.toList());
    }
}
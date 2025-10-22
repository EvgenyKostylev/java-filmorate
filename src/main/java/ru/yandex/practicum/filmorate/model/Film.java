package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;
import ru.yandex.practicum.filmorate.annotation.DateAfter;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Data
public class Film {
    private static final int MAX_DESCRIPTION_LENGTH = 200;

    private static final String MIN_RELEASE_DATE = "1895-12-28";

    private Long id;

    @NotBlank
    private final String name;

    @Size(max = MAX_DESCRIPTION_LENGTH)
    private final String description;

    @DateAfter(date = MIN_RELEASE_DATE)
    private final LocalDate releaseDate;

    @Positive
    private final long duration;

    private Set<Long> listUsersByIdLikes = new HashSet<>();

    public void addLike(long userId) {
        listUsersByIdLikes.add(userId);
    }

    public void removeLike(long userId) {
        listUsersByIdLikes.remove(userId);
    }

    public int getLikeCount() {
        return listUsersByIdLikes.size();
    }
}
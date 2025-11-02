package ru.yandex.practicum.filmorate.storage.like;

import ru.yandex.practicum.filmorate.model.Like;

import java.util.List;
import java.util.Optional;

public interface LikeStorage {
    Like save(long filmId, long userId);

    boolean delete(long filmId, long userId);

    Optional<Like> get(long filmId, long userId);

    List<Like> getCollection(long filmId);
}
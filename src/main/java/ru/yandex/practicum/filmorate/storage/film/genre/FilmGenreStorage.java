package ru.yandex.practicum.filmorate.storage.film.genre;

import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;

import java.util.List;

public interface FilmGenreStorage {
    List<FilmGenre> getAllById(long filmId);

    List<Long> saveAllById(long filmId, List<Genre> collection);

    boolean deleteAllById(long filmId);
}
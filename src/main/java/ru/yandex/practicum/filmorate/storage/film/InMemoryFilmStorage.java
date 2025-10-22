package ru.yandex.practicum.filmorate.storage.film;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class InMemoryFilmStorage implements FilmStorage {
    private final Map<Long, Film> listFilms = new HashMap<>();

    public Film create(Film film) {
        film.setId(getNextId());
        log.info("Создан обьект фильма: {}", film);
        listFilms.put(film.getId(), film);

        return film;
    }

    public Film update(Film film) {
        find(film.getId());
        log.info("Обновлен обьект фильма: {}", film);
        listFilms.put(film.getId(), film);

        return film;
    }

    public Film remove(long filmId) {
        find(filmId);

        Film removedFilm = listFilms.get(filmId);

        log.info("Удален обьект фильма: {}", removedFilm);
        listFilms.remove(filmId);

        return removedFilm;
    }

    public Film get(long filmId) {
        find(filmId);

        Film film = listFilms.get(filmId);

        log.info("Выведен обьект фильма: {}", film);

        return film;
    }

    public List<Film> getCollection() {
        return (List<Film>) listFilms.values();
    }

    private void find(long filmId) {
        if (filmId == 0) {
            log.warn("Отсутствует Id обьекта фильма: {}", filmId);
            throw new ValidationException("Id должен быть указан");
        }
        if (!listFilms.containsKey(filmId)) {
            log.warn("Фильм с Id {} не найден", filmId);
            throw new NotFoundException("Фильм с id = " + filmId + " не найден");
        }
    }

    private long getNextId() {
        long currentMaxId = listFilms.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
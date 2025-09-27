package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/films")
public class FilmController {
    private final Map<Integer, Film> listFilms = new HashMap<>();

    @PostMapping
    public Film create(@Valid @RequestBody Film film) {
        film.setId(getNextId());
        log.info("Создан обьект фильма: {}", film);
        listFilms.put(film.getId(), film);

        return film;
    }

    @PutMapping
    public Film update(@Valid @RequestBody Film film) {
        if (film.getId() == 0) {
            log.warn("Отсутствует Id обновляемого обьекта фильма: {}", film);
            throw new ValidationException("Id должен быть указан");
        }
        if (listFilms.containsKey(film.getId())) {
            log.info("Обновлен обьект фильма: {}", film);
            listFilms.put(film.getId(), film);

            return film;
        }
        log.warn("Фильм с Id {} не найден", film.getId());
        throw new NotFoundException("Фильм с id = " + film.getId() + " не найден");
    }

    @GetMapping
    public List<Film> getListFilms() {
        return new ArrayList<>(listFilms.values());
    }

    private int getNextId() {
        int currentMaxId = listFilms.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
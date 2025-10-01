package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.service.FilmService;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;
    private final FilmStorage filmStorage;

    @PostMapping
    public Film create(
            @Valid @RequestBody Film film) {
        return filmStorage.create(film);
    }

    @PutMapping
    public Film update(
            @Valid @RequestBody Film film) {
        return filmStorage.update(film);
    }

    @GetMapping("/{id}")
    public Film get(
            @PathVariable("id") long filmId) {
        return filmStorage.get(filmId);
    }

    @GetMapping
    public Collection<Film> getCollection() {
        return filmStorage.getCollection();
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(
            @PathVariable("id") long filmId,
            @PathVariable("userId") long userId) {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void removeLike(
            @PathVariable("id") long filmId,
            @PathVariable("userId") long userId) {
        filmService.removeLike(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<Film> getMostPopularCollection(
            @RequestParam long count) {
        return filmService.getMostPopularFilms(count);
    }
}
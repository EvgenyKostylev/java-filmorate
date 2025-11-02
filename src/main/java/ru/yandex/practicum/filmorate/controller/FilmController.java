package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.film.FilmDto;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.service.FilmService;

import java.util.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/films")
public class FilmController {
    private final FilmService filmService;

    @PostMapping
    public FilmDto create(
            @Valid @RequestBody NewFilmRequest request) {
        return filmService.save(request);
    }

    @PutMapping
    public FilmDto update(
            @Valid @RequestBody UpdateFilmRequest request) {
        return filmService.update(request);
    }

    @GetMapping("/{id}")
    public FilmDto get(
            @PathVariable("id") long filmId) {
        return filmService.get(filmId);
    }

    @GetMapping
    public Collection<FilmDto> getCollection() {
        return filmService.getCollection();
    }

    @PutMapping("/{id}/like/{userId}")
    public void addLike(
            @PathVariable("id") long filmId,
            @PathVariable("userId") long userId) {
        filmService.addLike(filmId, userId);
    }

    @DeleteMapping("/{id}/like/{userId}")
    public void deleteLike(
            @PathVariable("id") long filmId,
            @PathVariable("userId") long userId) {
        filmService.deleteLike(filmId, userId);
    }

    @GetMapping("/popular")
    public Collection<FilmDto> getMostPopularCollection(
            @RequestParam long count) {
        return filmService.getMostPopularFilms(count);
    }
}
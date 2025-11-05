package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.genre.GenreDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.mapper.GenreMapper;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class GenreService {
    public GenreService(@Autowired GenreStorage genreStorage) {
        this.genreStorage = genreStorage;
    }

    private final GenreStorage genreStorage;

    public GenreDto get(long genreId) {
        return genreStorage.get(genreId)
                .map(GenreMapper::mapToGenreDto)
                .orElseThrow(() -> new NotFoundException("Жанр с id = " + genreId + " не найден"));
    }

    public Collection<GenreDto> getAll() {
        return genreStorage.getAll()
                .stream()
                .map(GenreMapper::mapToGenreDto)
                .collect(Collectors.toList());
    }
}
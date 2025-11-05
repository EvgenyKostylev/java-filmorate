package ru.yandex.practicum.filmorate.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.rating.RatingDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.storage.mapper.RatingMapper;
import ru.yandex.practicum.filmorate.storage.rating.RatingStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class RatingService {
    public RatingService(@Autowired RatingStorage ratingStorage) {
        this.ratingStorage = ratingStorage;
    }

    private final RatingStorage ratingStorage;

    public RatingDto get(long ratingId) {
        return ratingStorage.get(ratingId)
                .map(RatingMapper::mapToRatingDto)
                .orElseThrow(() -> new NotFoundException("Рейтинг с id = " + ratingId + " не найден"));
    }

    public Collection<RatingDto> getAll() {
        return ratingStorage.getAll()
                .stream()
                .map(RatingMapper::mapToRatingDto)
                .collect(Collectors.toList());
    }
}
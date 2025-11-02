package ru.yandex.practicum.filmorate.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.filmorate.dto.rating.RatingDto;
import ru.yandex.practicum.filmorate.service.RatingService;

import java.util.Collection;

@RestController
@RequiredArgsConstructor
@RequestMapping("/mpa")
public class RatingController {
    private final RatingService ratingService;

    @GetMapping("/{id}")
    public RatingDto get(
            @PathVariable("id") long ratingId) {
        return ratingService.get(ratingId);
    }

    @GetMapping
    public Collection<RatingDto> getCollection() {
        return ratingService.getCollection();
    }
}
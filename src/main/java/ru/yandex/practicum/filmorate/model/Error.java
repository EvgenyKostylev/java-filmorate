package ru.yandex.practicum.filmorate.model;

import lombok.Data;

@Data
public class Error {
    private final String error;
    private final String description;
}
package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.Film;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class FilmControllerTest {
    private static FilmController controller;

    @BeforeAll
    public static void beforeAll() {
        controller = new FilmController();
    }

    @Test
    public void filmReleaseDateCantBeEarly28December1895Year() {
        Film film = new Film("name", "description", LocalDate.of(1895, 12, 27),
                100);

        assertThrows(ValidationException.class, () -> controller.create(film), "releaseDate может быть" +
                " раньше 28 декабря 1895 года");
    }

    @Test
    public void filmReleaseDateCanBe28December1895Year() {
        Film film = new Film("name", "description", LocalDate.of(1895, 12, 28),
                100);

        assertDoesNotThrow(() -> controller.create(film), "releaseDate не может быть 28 декабря 1895 года");
    }
}
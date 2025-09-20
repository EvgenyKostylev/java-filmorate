package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FilmTest {
    @Autowired
    private Validator validator;

    @Test
    public void filmNameCantBeNull() {
        Film film = new Film(null, "description", LocalDate.of(2000, 1, 1),
                100);
        Errors errors = new BeanPropertyBindingResult(film, "film");

        validator.validate(film, errors);
        assertTrue(errors.hasErrors(), "Валидация name не происходит");
        assertEquals(1, errors.getErrorCount(), "Количество ошибок не соответствует ожидаемому");
        assertNotNull(errors.getFieldError("name"), "Ошибка не связана с валидацией name");
    }

    @Test
    public void filmDescriptionLengthCantBeMore200Symbols() {
        Film film = new Film("name", "Настоящее описание характеризуется комплексным и" +
                " детализированным содержанием, объём которого, выраженный в символах с учётом пробелов и знаков" +
                " препинания, существенно превосходит установленную минимальную планку, составляющую двести единиц." +
                " Данный параметр был тщательно верифицирован для гарантии соответствия формальным критериям и" +
                " требованиям, предъявляемым к текстовым данным в рамках поставленной задачи.",
                LocalDate.of(2000, 1, 1), 100);
        Errors errors = new BeanPropertyBindingResult(film, "film");

        validator.validate(film, errors);
        assertTrue(errors.hasErrors(), "Валидация description не происходит");
        assertEquals(1, errors.getErrorCount(), "Количество ошибок не соответствует ожидаемому");
        assertNotNull(errors.getFieldError("description"), "Ошибка не связана с валидацией description");
    }

    @Test
    public void filmDescriptionLengthCanBe200Symbols() {
        Film film = new Film("name", "Данное текстовое описание было сознательно разработано для" +
                " достижения точного лимита в двести символов, включая все пробелы и знаки препинания. Двести" +
                " символов длинна этого текста с учетом всех знаков", LocalDate.of(2000, 1, 1),
                100);
        Errors errors = new BeanPropertyBindingResult(film, "film");

        validator.validate(film, errors);
        assertFalse(errors.hasErrors(), "Валидация description не соответствует ожиданиям");
    }

    @Test
    public void filmDurationCantBeNegative() {
        Film film = new Film("name", "description", LocalDate.of(2000, 1, 1),
                -100);
        Errors errors = new BeanPropertyBindingResult(film, "film");

        validator.validate(film, errors);
        assertTrue(errors.hasErrors(), "Валидация duration не происходит");
        assertEquals(1, errors.getErrorCount(), "Количество ошибок не соответствует ожидаемому");
        assertNotNull(errors.getFieldError("duration"), "Ошибка не связана с валидацией duration");
    }

    @Test
    public void filmDurationCantBe0() {
        Film film = new Film("name", "description", LocalDate.of(2000, 1, 1),
                0);
        Errors errors = new BeanPropertyBindingResult(film, "film");

        validator.validate(film, errors);
        assertTrue(errors.hasErrors(), "Валидация duration не происходит");
        assertEquals(1, errors.getErrorCount(), "Количество ошибок не соответствует ожидаемому");
        assertNotNull(errors.getFieldError("duration"), "Ошибка не связана с валидацией duration");
    }
}
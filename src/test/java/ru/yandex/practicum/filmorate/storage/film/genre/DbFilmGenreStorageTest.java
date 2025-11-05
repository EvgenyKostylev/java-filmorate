package ru.yandex.practicum.filmorate.storage.film.genre;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.film.DbFilmStorage;
import ru.yandex.practicum.filmorate.storage.mapper.FilmGenreRowMapper;
import ru.yandex.practicum.filmorate.storage.mapper.FilmRowMapper;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({DbFilmGenreStorage.class,
        FilmGenreRowMapper.class,
        DbFilmStorage.class,
        FilmRowMapper.class})
public class DbFilmGenreStorageTest {
    private final DbFilmGenreStorage dbFilmGenreStorage;
    private final DbFilmStorage dbFilmStorage;

    @Test
    public void testSaveCollection() {
        Film film = dbFilmStorage.save(Film.builder()
                .name("film")
                .description("film description")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148L)
                .build());

        List<Genre> genres = List.of(
                Genre.builder()
                        .id(1L)
                        .build(),
                Genre.builder()
                        .id(2L)
                        .build()
        );

        List<Long> savedIds = dbFilmGenreStorage.saveAllById(film.getId(), genres);

        assertThat(savedIds).containsExactlyInAnyOrder(1L, 2L);

        List<FilmGenre> collection = dbFilmGenreStorage.getAllById(film.getId());

        assertThat(collection).isNotEmpty();
        assertThat(collection.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void testRemoveCollection() {
        Film film = dbFilmStorage.save(Film.builder()
                .name("film")
                .description("film description")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148L)
                .build());

        List<Genre> genres = List.of(
                Genre.builder()
                        .id(1L)
                        .build(),
                Genre.builder()
                        .id(2L)
                        .build()
        );

        dbFilmGenreStorage.saveAllById(film.getId(), genres);

        boolean removed = dbFilmGenreStorage.deleteAllById(film.getId());

        assertThat(removed).isTrue();

        List<FilmGenre> collectionAfterRemoval = dbFilmGenreStorage.getAllById(film.getId());

        assertThat(collectionAfterRemoval).isEmpty();
    }

    @Test
    public void testGetCollection() {
        Film film = dbFilmStorage.save(Film.builder()
                .name("film")
                .description("film description")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148L)
                .build());

        List<Genre> genres = List.of(
                Genre.builder()
                        .id(1L)
                        .build(),
                Genre.builder()
                        .id(2L)
                        .build()
        );

        dbFilmGenreStorage.saveAllById(film.getId(), genres);

        List<FilmGenre> collection = dbFilmGenreStorage.getAllById(film.getId());

        assertThat(collection).isNotEmpty();
        assertThat(collection.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    public void testSaveEmptyCollection() {
        Film film = dbFilmStorage.save(Film.builder()
                .name("film")
                .description("film description")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148L)
                .build());

        List<Genre> genres = List.of();

        List<Long> savedIds = dbFilmGenreStorage.saveAllById(film.getId(), genres);

        assertThat(savedIds).isEmpty();
        assertThat(dbFilmGenreStorage.getAllById(film.getId())).isEmpty();
    }
}
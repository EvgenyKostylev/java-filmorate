package ru.yandex.practicum.filmorate.storage.film;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.mapper.FilmRowMapper;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({DbFilmStorage.class,
        FilmRowMapper.class})
class DbFilmStorageTest {
    private final DbFilmStorage dbFilmStorage;

    @Test
    void testCreateFilm() {
        Film created = dbFilmStorage.save(Film.builder()
                .name("film")
                .description("film description")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148L)
                .build());

        assertThat(created.getId()).isPositive();

        Optional<Film> loaded = dbFilmStorage.get(created.getId());

        assertThat(loaded).isPresent();
        assertThat(loaded.get().getName()).isEqualTo("film");
        assertThat(loaded.get().getDescription()).isEqualTo("film description");
    }

    @Test
    void testFindFilmById() {
        Film created = dbFilmStorage.save(Film.builder()
                .name("film")
                .description("film description")
                .releaseDate(LocalDate.of(2014, 11, 7))
                .duration(169L)
                .build());

        Optional<Film> loaded = dbFilmStorage.get(created.getId());

        assertThat(loaded)
                .isPresent()
                .get()
                .hasFieldOrPropertyWithValue("name", "film")
                .hasFieldOrPropertyWithValue("description", "film description");
    }

    @Test
    void testUpdateFilm() {
        Film film = Film.builder()
                .name("old film")
                .description("old film description")
                .releaseDate(LocalDate.of(2000, 1, 1))
                .duration(100L)
                .build();

        Film created = dbFilmStorage.save(film);

        created.setName("new film");
        created.setDescription("new film description");
        created.setDuration(120L);
        dbFilmStorage.update(created);

        Optional<Film> updated = dbFilmStorage.get(created.getId());

        assertThat(updated).isPresent();
        assertThat(updated.get().getName()).isEqualTo("new film");
        assertThat(updated.get().getDescription()).isEqualTo("new film description");
        assertThat(updated.get().getDuration()).isEqualTo(120);
    }

    @Test
    void testGetAllFilms() {
        dbFilmStorage.save(Film.builder()
                .name("film one")
                .description("film one description")
                .releaseDate(LocalDate.of(2001, 1, 1))
                .duration(100L)
                .build());
        dbFilmStorage.save(Film.builder()
                .name("film two")
                .description("film two description")
                .releaseDate(LocalDate.of(2002, 2, 2))
                .duration(110L)
                .build());

        List<Film> films = dbFilmStorage.getCollection();

        assertThat(films).isNotEmpty();
        assertThat(films.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void testGetFilmNotFound() {
        Optional<Film> missing = dbFilmStorage.get(9999L);

        assertThat(missing).isEmpty();
    }
}
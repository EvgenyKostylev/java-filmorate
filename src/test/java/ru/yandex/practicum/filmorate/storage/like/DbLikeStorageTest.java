package ru.yandex.practicum.filmorate.storage.like;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.film.DbFilmStorage;
import ru.yandex.practicum.filmorate.storage.mapper.FilmRowMapper;
import ru.yandex.practicum.filmorate.storage.mapper.LikeRowMapper;
import ru.yandex.practicum.filmorate.storage.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.storage.user.DbUserStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({DbLikeStorage.class,
        LikeRowMapper.class,
        DbUserStorage.class,
        UserRowMapper.class,
        DbFilmStorage.class,
        FilmRowMapper.class})
public class DbLikeStorageTest {
    private final DbLikeStorage dbLikeStorage;
    private final DbUserStorage dbUserStorage;
    private final DbFilmStorage dbFilmStorage;

    @Test
    public void testSaveLike() {
        User user = dbUserStorage.save(User.builder()
                .email("user@mail.com")
                .login("user").name("User")
                .birthday(LocalDate.of(1990, 1, 1))
                .build());

        Film film = dbFilmStorage.save(Film.builder()
                .name("film")
                .description("film description")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148L)
                .build());

        Like created = dbLikeStorage.save(film.getId(), user.getId());

        assertThat(created.getId()).isPositive();
        assertThat(created.getFilmId()).isEqualTo(film.getId());
        assertThat(created.getUserId()).isEqualTo(user.getId());

        Optional<Like> loaded = dbLikeStorage.get(film.getId(), user.getId());

        assertThat(loaded).isPresent();
        assertThat(loaded.get().getFilmId()).isEqualTo(film.getId());
        assertThat(loaded.get().getUserId()).isEqualTo(user.getId());
    }

    @Test
    public void testFindLikeByIds() {
        User user = dbUserStorage.save(User.builder()
                .email("user@mail.com")
                .login("user").name("User")
                .birthday(LocalDate.of(1990, 1, 1))
                .build());

        Film film = dbFilmStorage.save(Film.builder()
                .name("film")
                .description("film description")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148L)
                .build());

        dbLikeStorage.save(film.getId(), user.getId());

        Optional<Like> found = dbLikeStorage.get(film.getId(), user.getId());

        assertThat(found).isPresent()
                .get()
                .hasFieldOrPropertyWithValue("filmId", film.getId())
                .hasFieldOrPropertyWithValue("userId", user.getId());
    }

    @Test
    public void testRemoveLike() {
        User user = dbUserStorage.save(User.builder()
                .email("user@mail.com")
                .login("user").name("User")
                .birthday(LocalDate.of(1990, 1, 1))
                .build());

        Film film = dbFilmStorage.save(Film.builder()
                .name("film")
                .description("film description")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148L)
                .build());

        dbLikeStorage.save(film.getId(), user.getId());

        boolean removed = dbLikeStorage.delete(film.getId(), user.getId());

        assertThat(removed).isTrue();

        Optional<Like> deleted = dbLikeStorage.get(film.getId(), user.getId());

        assertThat(deleted).isEmpty();
    }

    @Test
    public void testGetAllLikesByFilmId() {
        Film film = dbFilmStorage.save(Film.builder()
                .name("film")
                .description("film description")
                .releaseDate(LocalDate.of(2010, 7, 16))
                .duration(148L)
                .build());

        User user1 = dbUserStorage.save(User.builder()
                .email("user1@mail.com")
                .login("user1").name("user two")
                .birthday(LocalDate.of(1990, 1, 1))
                .build());

        User user2 = dbUserStorage.save(User.builder()
                .email("user2@mail.com")
                .login("user2").name("user one")
                .birthday(LocalDate.of(1990, 1, 1))
                .build());

        dbLikeStorage.save(film.getId(), user1.getId());
        dbLikeStorage.save(film.getId(), user2.getId());

        List<Like> likes = dbLikeStorage.getAllById(film.getId());

        assertThat(likes).isNotEmpty();
        assertThat(likes.size()).isGreaterThanOrEqualTo(2);
    }
}
package ru.yandex.practicum.filmorate.storage.genre;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.mapper.GenreRowMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({DbGenreStorage.class,
        GenreRowMapper.class})
public class DbGenreStorageTest {
    private final DbGenreStorage dbGenreStorage;

    @Test
    public void testFindGenreById() {
        Optional<Genre> genre = dbGenreStorage.get(1L);

        assertThat(genre).isPresent()
                .get()
                .hasFieldOrProperty("id")
                .hasFieldOrProperty("name");
    }

    @Test
    public void testGetAllGenres() {
        List<Genre> genres = dbGenreStorage.getAll();

        assertThat(genres).isNotEmpty();
    }
}
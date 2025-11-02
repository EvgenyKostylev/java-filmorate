package ru.yandex.practicum.filmorate.storage.genre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.DbBaseStorage;

import java.util.List;
import java.util.Optional;

@Repository
public class DbGenreStorage extends DbBaseStorage<Genre> implements GenreStorage {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM genres WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * FROM genres";

    public DbGenreStorage(JdbcTemplate jdbc, RowMapper<Genre> mapper) {
        super(jdbc, mapper);
    }

    public Optional<Genre> get(long genreId) {
        return findOne(FIND_BY_ID_QUERY, genreId);
    }

    public List<Genre> getCollection() {
        return findMany(FIND_ALL_QUERY);
    }
}
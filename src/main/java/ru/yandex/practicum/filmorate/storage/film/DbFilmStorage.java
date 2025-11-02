package ru.yandex.practicum.filmorate.storage.film;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.DbBaseStorage;

import java.util.List;
import java.util.Optional;

@Repository
public class DbFilmStorage extends DbBaseStorage<Film> implements FilmStorage {
    private static final String FIND_ALL_QUERY = "SELECT * FROM films";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM films WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO films(name, description, release_date, duration, rating)" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE films SET name = ?, " +
            "description = ?, " +
            "release_date = ?, " +
            "duration = ?, " +
            "rating = ? " +
            "WHERE id = ?";

    public DbFilmStorage(JdbcTemplate jdbc, RowMapper<Film> mapper) {
        super(jdbc, mapper);
    }

    public Optional<Film> get(long filmId) {
        return findOne(FIND_BY_ID_QUERY, filmId);
    }

    public List<Film> getCollection() {
        return findMany(FIND_ALL_QUERY);
    }

    public Film save(Film film) {
        long id = insert(
                INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRatingId()
        );

        film.setId(id);

        return film;
    }

    public Film update(Film film) {
        update(
                UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                film.getRatingId(),
                film.getId()
        );

        return film;
    }

    public Film delete(long filmId) {
        throw new RuntimeException("Not implemented");
    }
}
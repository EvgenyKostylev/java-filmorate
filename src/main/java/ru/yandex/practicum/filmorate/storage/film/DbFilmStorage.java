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
    private static final String FIND_ALL_QUERY = "SELECT f.id, " +
            "f.name, " +
            "f.description, " +
            "f.release_date, " +
            "f.duration, " +
            "r.id AS rating_id, " +
            "r.name AS rating_name, " +
            "GROUP_CONCAT(g.id || ':' || g.name SEPARATOR ',') AS genres " +
            "FROM films f " +
            "LEFT JOIN ratings r ON f.rating = r.id " +
            "LEFT JOIN film_genres fg ON f.id = fg.film_id " +
            "LEFT JOIN genres g ON fg.genre_id = g.id " +
            "GROUP BY f.id";
    private static final String FIND_BY_ID_QUERY = "SELECT f.id, " +
            "f.name, " +
            "f.description, " +
            "f.release_date, " +
            "f.duration, " +
            "r.id AS rating_id, " +
            "r.name AS rating_name, " +
            "GROUP_CONCAT(g.id || ':' || g.name SEPARATOR ',') AS genres " +
            "FROM films f " +
            "LEFT JOIN ratings r ON f.rating = r.id " +
            "LEFT JOIN film_genres fg ON f.id = fg.film_id " +
            "LEFT JOIN genres g ON fg.genre_id = g.id " +
            "WHERE f.id = ? " +
            "GROUP BY f.id";
    private static final String INSERT_QUERY = "INSERT INTO films(name, description, release_date, duration, rating)" +
            "VALUES (?, ?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE films " +
            "SET name = ?, " +
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

    public List<Film> getAll() {
        return findMany(FIND_ALL_QUERY);
    }

    public Film save(Film film) {
        Long ratingId;

        if (film.getRating() == null) {
            ratingId = null;
        } else {
            ratingId = film.getRating().getId();
        }

        long id = insert(
                INSERT_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                ratingId
        );

        film.setId(id);

        return film;
    }

    public Film update(Film film) {
        Long ratingId;

        if (film.getRating() == null) {
            ratingId = null;
        } else {
            ratingId = film.getRating().getId();
        }

        update(
                UPDATE_QUERY,
                film.getName(),
                film.getDescription(),
                film.getReleaseDate(),
                film.getDuration(),
                ratingId,
                film.getId()
        );

        return film;
    }
}
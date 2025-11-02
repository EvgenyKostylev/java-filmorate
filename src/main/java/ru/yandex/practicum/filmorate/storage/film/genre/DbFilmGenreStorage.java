package ru.yandex.practicum.filmorate.storage.film.genre;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.storage.DbBaseStorage;

import java.util.*;

@Repository
public class DbFilmGenreStorage extends DbBaseStorage<FilmGenre> implements FilmGenreStorage {
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM film_genres WHERE film_id = ?";
    private static final String INSERT_QUERY = "INSERT INTO film_genres(film_id, genre_id)" +
            "VALUES (?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM film_genres WHERE film_id = ?";

    public DbFilmGenreStorage(JdbcTemplate jdbc, RowMapper<FilmGenre> mapper) {
        super(jdbc, mapper);
    }

    public List<FilmGenre> getCollection(long filmId) {
        return findMany(FIND_BY_ID_QUERY, filmId);
    }

    public List<Long> saveCollection(long filmId, List<Genre> collection) {
        deleteCollection(filmId);

        if (collection == null || collection.isEmpty()) {
            return Collections.emptyList();
        }

        return collection
                .stream()
                .distinct()
                .map(genre -> {
                    insert(INSERT_QUERY,
                            filmId,
                            genre.getId());
                    return genre.getId();
                }).toList();
    }

    public boolean deleteCollection(long filmId) {
        return delete(DELETE_QUERY, filmId);
    }
}
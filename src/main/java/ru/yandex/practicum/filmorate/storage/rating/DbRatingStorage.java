package ru.yandex.practicum.filmorate.storage.rating;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.DbBaseStorage;

import java.util.List;
import java.util.Optional;

@Repository
public class DbRatingStorage extends DbBaseStorage<Rating> implements RatingStorage {
    private static final String FIND_BY_ID_QUERY = "SELECT * " +
            "FROM ratings " +
            "WHERE id = ?";
    private static final String FIND_ALL_QUERY = "SELECT * " +
            "FROM ratings";

    public DbRatingStorage(JdbcTemplate jdbc, RowMapper<Rating> mapper) {
        super(jdbc, mapper);
    }

    public Optional<Rating> get(long genreId) {
        return findOne(FIND_BY_ID_QUERY, genreId);
    }

    public List<Rating> getAll() {
        return findMany(FIND_ALL_QUERY);
    }
}
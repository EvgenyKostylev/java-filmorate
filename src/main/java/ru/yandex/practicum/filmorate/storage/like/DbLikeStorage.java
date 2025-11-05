package ru.yandex.practicum.filmorate.storage.like;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.Like;
import ru.yandex.practicum.filmorate.storage.DbBaseStorage;

import java.util.List;
import java.util.Optional;

@Repository
public class DbLikeStorage extends DbBaseStorage<Like> implements LikeStorage {
    private static final String FIND_BY_IDS_QUERY = "SELECT * " +
            "FROM likes " +
            "WHERE film_id = ? " +
            "AND user_id = ?";
    private static final String FIND_ALL_BY_ID_QUERY = "SELECT * " +
            "FROM likes " +
            "WHERE film_id = ?";
    private static final String INSERT_QUERY = "INSERT INTO likes(film_id, user_id)" +
            "VALUES (?, ?)";
    private static final String DELETE_QUERY = "DELETE FROM likes " +
            "WHERE film_id = ? " +
            "AND user_id = ?";

    public DbLikeStorage(JdbcTemplate jdbc, RowMapper<Like> mapper) {
        super(jdbc, mapper);
    }

    public Like save(long filmId, long userId) {
        long id = insert(INSERT_QUERY,
                filmId,
                userId);

        return Like.builder()
                .id(id).filmId(filmId)
                .userId(userId)
                .build();
    }

    public boolean delete(long filmId, long userId) {
        return delete(DELETE_QUERY, filmId, userId);
    }

    public Optional<Like> get(long filmId, long userId) {
        return findOne(FIND_BY_IDS_QUERY, filmId, userId);
    }

    public List<Like> getAllById(long filmId) {
        return findMany(FIND_ALL_BY_ID_QUERY, filmId);
    }
}
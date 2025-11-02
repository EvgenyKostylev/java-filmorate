package ru.yandex.practicum.filmorate.storage.friendship;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.FriendStatus;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.storage.DbBaseStorage;

import java.util.List;
import java.util.Optional;

@Repository
public class DbFriendshipStorage extends DbBaseStorage<Friendship> implements FriendshipStorage {
    private static final String INSERT_QUERY = "INSERT INTO friendships(first_user_id, second_user_id, status)" +
            "VALUES (?, ?, ?)";
    private static final String FIND_BY_IDS_QUERY = "SELECT * FROM friendships WHERE (first_user_Id = ? " +
            "AND second_user_id = ?) " +
            "OR (first_user_id = ? " +
            "AND second_user_id = ?)";
    private static final String DELETE_QUERY = "DELETE FROM friendships WHERE id = ?";
    private static final String UPDATE_QUERY = "UPDATE friendships SET status = ? WHERE id = ?";
    private static final String FIND_BY_ID_QUERY = "SELECT * FROM friendships WHERE first_user_Id = ? " +
            "OR second_user_id = ?";

    public DbFriendshipStorage(JdbcTemplate jdbc, RowMapper<Friendship> mapper) {
        super(jdbc, mapper);
    }

    public void save(long firstUserId, long secondUserId) {
        insert(INSERT_QUERY,
                firstUserId,
                secondUserId,
                FriendStatus.PENDING.toString());
    }

    public void accept(long friendshipId) {
        insert(UPDATE_QUERY,
                FriendStatus.CONFIRMED.toString(),
                friendshipId);
    }

    public void delete(long friendshipId) {
        delete(DELETE_QUERY, friendshipId);
    }

    public Optional<Friendship> get(long firstUserId, long secondUserId) {
        return findOne(FIND_BY_IDS_QUERY, firstUserId, secondUserId, secondUserId, firstUserId);
    }

    public List<Friendship> getCollection(long userId) {
        return findMany(FIND_BY_ID_QUERY, userId, userId);
    }
}
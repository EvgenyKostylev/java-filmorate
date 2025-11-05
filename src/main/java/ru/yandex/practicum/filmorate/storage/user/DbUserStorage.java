package ru.yandex.practicum.filmorate.storage.user;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.DbBaseStorage;

import java.util.List;
import java.util.Optional;

@Repository
public class DbUserStorage extends DbBaseStorage<User> implements UserStorage {
    private static final String FIND_ALL_QUERY = "SELECT * " +
            "FROM users";
    private static final String FIND_BY_ID_QUERY = "SELECT * " +
            "FROM users " +
            "WHERE id = ?";
    private static final String INSERT_QUERY = "INSERT INTO users(email, login, name, birthday)" +
            "VALUES (?, ?, ?, ?)";
    private static final String UPDATE_QUERY = "UPDATE users " +
            "SET email = ?, " +
            "login = ?, " +
            "name = ?, " +
            "birthday = ? " +
            "WHERE id = ?";

    public DbUserStorage(JdbcTemplate jdbc, RowMapper<User> mapper) {
        super(jdbc, mapper);
    }

    public Optional<User> get(long userId) {
        return findOne(FIND_BY_ID_QUERY, userId);
    }

    public List<User> getAll() {
        return findMany(FIND_ALL_QUERY);
    }

    public User save(User user) {
        long id = insert(
                INSERT_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday()
        );

        user.setId(id);

        return user;
    }

    public User update(User user) {
        update(
                UPDATE_QUERY,
                user.getEmail(),
                user.getLogin(),
                user.getName(),
                user.getBirthday(),
                user.getId()
        );

        return user;
    }
}
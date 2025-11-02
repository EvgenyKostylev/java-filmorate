package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FriendStatus;
import ru.yandex.practicum.filmorate.model.Friendship;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FriendshipRowMapper implements RowMapper<Friendship> {
    @Override
    public Friendship mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return Friendship.builder()
                .id(resultSet.getLong("id"))
                .firstUserId(resultSet.getLong("first_user_id"))
                .secondUserId(resultSet.getLong("second_user_id"))
                .status(FriendStatus.valueOf(resultSet.getString("status")))
                .build();
    }
}
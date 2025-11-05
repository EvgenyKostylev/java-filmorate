package ru.yandex.practicum.filmorate.storage.friendship;

import ru.yandex.practicum.filmorate.model.Friendship;

import java.util.List;
import java.util.Optional;

public interface FriendshipStorage {
    void save(long firstUserId, long secondUserId);

    void accept(long friendshipId);

    void delete(long friendshipId);

    Optional<Friendship> get(long firstUserId, long secondUserId);

    List<Friendship> getAllById(long userId);
}
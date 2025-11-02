package ru.yandex.practicum.filmorate.storage.user;

import ru.yandex.practicum.filmorate.model.User;

import java.util.List;
import java.util.Optional;

public interface UserStorage {
    User save(User user);

    User update(User user);

    User delete(long userId);

    Optional<User> get(long userId);

    List<User> getCollection();
}
package ru.yandex.practicum.filmorate.storage.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class InMemoryUserStorage implements UserStorage {
    private final Map<Long, User> listUsers = new HashMap<>();

    public User create(User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        log.info("Создан обьект пользователя: {}", user);
        listUsers.put(user.getId(), user);

        return user;
    }

    public User update(User user) {
        find(user.getId());

        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        log.info("Обновлен обьект пользователя: {}", user);
        listUsers.put(user.getId(), user);

        return user;
    }

    public User remove(long userId) {
        find(userId);

        User removedUser = listUsers.get(userId);

        log.info("Удален обьект пользователя: {}", removedUser);
        listUsers.remove(userId);

        return removedUser;
    }

    public User get(long userId) {
        find(userId);

        User user = listUsers.get(userId);

        log.info("Выведен обьект пользователя: {}", user);

        return user;
    }

    public Collection<User> getCollection() {
        return listUsers.values();
    }

    private void find(long userId) {
        if (userId == 0) {
            log.warn("Отсутствует Id обьекта пользователя: {}", userId);
            throw new ValidationException("Id должен быть указан");
        }
        if (!listUsers.containsKey(userId)) {
            log.warn("Пользователь с Id {} не найден", userId);
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
    }

    private long getNextId() {
        long currentMaxId = listUsers.keySet()
                .stream()
                .mapToLong(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
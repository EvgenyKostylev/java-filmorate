package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.util.*;

@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final Map<Integer, User> listUsers = new HashMap<>();

    @PostMapping
    public User create(@Valid @RequestBody User user) {
        if (user.getName() == null) {
            user.setName(user.getLogin());
        }
        user.setId(getNextId());
        log.info("Создан обьект пользователя: {}", user);
        listUsers.put(user.getId(), user);

        return user;
    }

    @PutMapping
    public User update(@Valid @RequestBody User user) {
        if (user.getId() == 0) {
            log.warn("Отсутствует Id обновляемого обьекта пользователя: {}", user);
            throw new ValidationException("Id должен быть указан");
        }
        if (listUsers.containsKey(user.getId())) {
            if (user.getName() == null) {
                user.setName(user.getLogin());
            }
            log.info("Обновлен обьект пользователя: {}", user);
            listUsers.put(user.getId(), user);

            return user;
        }
        log.warn("Обьект пользователя с Id {} не найден", user.getId());
        throw new NotFoundException("Пользователь с id = " + user.getId() + " не найден");
    }

    @GetMapping
    public Collection<User> getListUsers() {
        return listUsers.values();
    }

    private int getNextId() {
        int currentMaxId = listUsers.keySet()
                .stream()
                .mapToInt(id -> id)
                .max()
                .orElse(0);
        return ++currentMaxId;
    }
}
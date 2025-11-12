package ru.yandex.practicum.filmorate.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.filmorate.dto.user.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UserDto;
import ru.yandex.practicum.filmorate.service.UserService;

import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping
    public UserDto save(@Valid @RequestBody NewUserRequest request) {
        return userService.save(request);
    }

    @PutMapping
    public UserDto update(@Valid @RequestBody UpdateUserRequest request) {
        return userService.update(request);
    }

    @GetMapping("/{id}")
    public UserDto get(@PathVariable("id") long userId) {
        return userService.get(userId);
    }

    @GetMapping
    public Collection<UserDto> getAll() {
        return userService.getAll();
    }

    @PutMapping("/{id}/friends/{friendId}")
    public void addFriend(
            @PathVariable("id") long userId,
            @PathVariable("friendId") long friendId) {
        userService.addToFriends(userId, friendId);
    }

    @DeleteMapping("/{id}/friends/{friendId}")
    public void deleteFriend(
            @PathVariable("id") long userId,
            @PathVariable("friendId") long friendId) {
        userService.deleteFromFriends(userId, friendId);
    }

    @GetMapping("/{id}/friends")
    public Collection<UserDto> getAllFriends(
            @PathVariable("id") long userId) {
        return userService.getAllFriends(userId);
    }

    @GetMapping("/{id}/friends/common/{otherId}")
    public Collection<UserDto> getAllMutualFriends(
            @PathVariable("id") long userId,
            @PathVariable("otherId") long friendId) {
        return userService.getMutualFriends(userId, friendId);
    }
}
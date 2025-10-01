package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserStorage userStorage;

    public void addToFriends(long userId, long friendId) {
        User user = userStorage.get(userId);
        User friend = userStorage.get(friendId);

        user.addFriend(friendId);
        friend.addFriend(userId);
        log.info("Пользователь {} добавил в друзья пользователя {}", user, friend);
    }

    public void removeFromFriends(long userId, long friendId) {
        User user = userStorage.get(userId);
        User friend = userStorage.get(friendId);

        user.removeFriend(friendId);
        friend.removeFriend(userId);
        log.info("Пользователь {} убрал из друзей пользователя {}", user, friend);
    }

    public Collection<User> getListFriends(long userId) {
        User user = userStorage.get(userId);

        return userStorage.getCollection()
                .stream()
                .filter(userFromStorage -> user.getFriends().contains(userFromStorage.getId()))
                .collect(Collectors.toList());
    }

    public Collection<User> getListMutualFriends(long userId, long friendId) {
        Collection<User> listUserFriends = getListFriends(userId);
        Collection<User> listFriendFriends = getListFriends(friendId);

        return listUserFriends.stream().filter(listFriendFriends::contains).collect(Collectors.toList());
    }
}
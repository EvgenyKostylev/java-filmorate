package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.user.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UserDto;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.FriendStatus;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.friendship.FriendshipStorage;
import ru.yandex.practicum.filmorate.storage.mapper.UserMapper;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class UserService {
    public UserService(@Autowired @Qualifier("dbUserStorage") UserStorage userStorage,
                       @Autowired FriendshipStorage friendshipStorage) {
        this.userStorage = userStorage;
        this.friendshipStorage = friendshipStorage;
    }

    private final UserStorage userStorage;
    private final FriendshipStorage friendshipStorage;

    public UserDto save(NewUserRequest request) {
        User user = UserMapper.mapToUser(request);

        user = userStorage.save(user);

        return UserMapper.mapToUserDto(user);
    }

    public UserDto update(UpdateUserRequest request) {
        User updatedUser = userStorage.get(request.getId())
                .map(user -> UserMapper.updateUserFields(user, request))
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + request.getId() + " не найден"));

        updatedUser = userStorage.update(updatedUser);

        return UserMapper.mapToUserDto(updatedUser);
    }

    public UserDto get(long userId) {
        return userStorage.get(userId)
                .map(UserMapper::mapToUserDto)
                .orElseThrow(() -> new NotFoundException("Пользователь с id = " + userId + " не найден"));
    }

    public Collection<UserDto> getCollection() {
        return userStorage.getCollection()
                .stream()
                .map(UserMapper::mapToUserDto)
                .collect(Collectors.toList());
    }

    public void addToFriends(long userId, long friendId) {
        ensureUserExists(userId);
        ensureUserExists(friendId);

        Optional<Friendship> friendshipOptional = friendshipStorage.get(userId, friendId);

        if (friendshipOptional.isPresent()) {
            if (userId == friendshipOptional.get().getSecondUserId()) {
                if (friendshipOptional.get().getStatus() == FriendStatus.PENDING) {
                    friendshipStorage.accept(friendshipOptional.get().getId());
                }
            }
        } else {
            friendshipStorage.save(userId, friendId);
        }

        log.info("Пользователь {} добавил в друзья пользователя {}", userId, friendId);
    }

    public void deleteFromFriends(long userId, long friendId) {
        ensureUserExists(userId);
        ensureUserExists(friendId);

        Optional<Friendship> friendshipOptional = friendshipStorage.get(userId, friendId);

        if (friendshipOptional.isPresent()) {
            if (friendshipOptional.get().getStatus() == FriendStatus.CONFIRMED
                    || friendshipOptional.get().getFirstUserId() == userId) {
                friendshipStorage.delete(friendshipOptional.get().getId());
            }
        }

        log.info("Пользователь {} убрал из друзей пользователя {}", userId, friendId);
    }

    public Collection<UserDto> getFriendCollection(long userId) {
        ensureUserExists(userId);

        List<Friendship> friendships = friendshipStorage.getCollection(userId)
                .stream()
                .filter(friendship -> friendship.getFirstUserId() == userId
                        || friendship.getStatus() == FriendStatus.CONFIRMED).toList();

        return friendships.stream().map(friendship -> {
            if (friendship.getFirstUserId() == userId) {
                return userStorage.get(friendship.getSecondUserId()).get();
            } else {
                return userStorage.get(friendship.getFirstUserId()).get();
            }
        }).map(UserMapper::mapToUserDto).toList();
    }

    public Collection<UserDto> getMutualFriendCollection(long userId, long friendId) {
        ensureUserExists(userId);
        ensureUserExists(friendId);

        Collection<UserDto> listUserFriends = getFriendCollection(userId);
        Collection<UserDto> listFriendFriends = getFriendCollection(friendId);

        return listUserFriends.stream().filter(listFriendFriends::contains).collect(Collectors.toList());
    }

    private void ensureUserExists(long userId) {
        if (userStorage.get(userId).isEmpty()) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
    }
}
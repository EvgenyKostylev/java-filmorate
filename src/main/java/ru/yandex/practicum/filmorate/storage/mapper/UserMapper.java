package ru.yandex.practicum.filmorate.storage.mapper;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import ru.yandex.practicum.filmorate.dto.user.NewUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UpdateUserRequest;
import ru.yandex.practicum.filmorate.dto.user.UserDto;
import ru.yandex.practicum.filmorate.model.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UserMapper {
    public static User mapToUser(NewUserRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .login(request.getLogin())
                .birthday(request.getBirthday())
                .build();

        if (request.getName() == null || request.getName().isEmpty()) {
            user.setName(request.getLogin());
        } else {
            user.setName(request.getName());
        }

        return user;
    }

    public static UserDto mapToUserDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .login(user.getLogin())
                .name(user.getName())
                .birthday(user.getBirthday())
                .build();
    }

    public static User updateUserFields(User user, UpdateUserRequest request) {
        if (request.hasEmail()) {
            user.setEmail(request.getEmail());
        }
        if (request.hasLogin()) {
            user.setLogin(request.getLogin());
        }
        if (request.hasName()) {
            user.setName(request.getName());
        }
        if (request.hasBirthday()) {
            user.setBirthday(request.getBirthday());
        }

        return user;
    }
}
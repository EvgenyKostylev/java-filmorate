package ru.yandex.practicum.filmorate.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.dto.user.NewUserRequest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureTestDatabase
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void userNameUseLoginIfNameIsEmpty() {
        NewUserRequest user = new NewUserRequest(
                "Login@Mail",
                "Login",
                null,
                LocalDate.of(2000, 1, 1)
        );

        assertEquals(
                user.getLogin(),
                userService.save(user).getName(),
                "Поле name пользователя не соответствует полю login, в случае пустого поля name"
        );
    }
}
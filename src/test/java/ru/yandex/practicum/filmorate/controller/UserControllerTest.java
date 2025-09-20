package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.filmorate.exception.ValidationException;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

public class UserControllerTest {
    private static UserController controller;

    @BeforeAll
    public static void beforeAll() {
        controller = new UserController();
    }

    @Test
    public void userLoginCantExistWithWhitespaces() {
        User user = new User("Login@Mail", "Not Validate Login", LocalDate.of(2000, 1,
                1));

        assertThrows(ValidationException.class, () -> controller.create(user), "Принимает login с символами" +
                " пробела");
    }

    @Test
    public void userNameUseLoginIfNameIsEmpty() {
        User user = new User("Login@Mail", "Login", LocalDate.of(2000, 1, 1));

        assertEquals(user.getLogin(), controller.create(user).getName(), "Name пользователя не соответствует" +
                " Login если Name пустое");
    }
}
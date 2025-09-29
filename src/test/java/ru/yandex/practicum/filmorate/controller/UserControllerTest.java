package ru.yandex.practicum.filmorate.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.yandex.practicum.filmorate.model.User;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserControllerTest {
    @Autowired
    private UserController controller;

    @Test
    public void userNameUseLoginIfNameIsEmpty() {
        User user = new User("Login@Mail", "Login", LocalDate.of(2000, 1, 1));

        assertEquals(user.getLogin(), controller.create(user).getName(), "Name пользователя не соответствует" +
                " Login если Name пустое");
    }
}
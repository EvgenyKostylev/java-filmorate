package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {
    @Autowired
    private Validator validator;

    @Test
    public void userLoginCantExistWithWhitespaces() {
        User user = new User("Login@Mail", "Not Validate Login", LocalDate.of(2000, 1,
                1));
        Errors errors = new BeanPropertyBindingResult(user, "user");

        validator.validate(user, errors);
        assertTrue(errors.hasErrors(), "Валидация login не происходит");
        assertEquals(1, errors.getErrorCount(), "Количество ошибок не соответствует ожидаемому");
        assertNotNull(errors.getFieldError("login"), "Ошибка не связана с валидацией login");
    }

    @Test
    public void userEmailCantBeNull() {
        User user = new User(null, "login", LocalDate.of(2000, 1, 1));
        Errors errors = new BeanPropertyBindingResult(user, "user");

        validator.validate(user, errors);
        assertTrue(errors.hasErrors(), "Валидация email не происходит");
        assertEquals(1, errors.getErrorCount(), "Количество ошибок не соответствует ожидаемому");
        assertNotNull(errors.getFieldError("email"), "Ошибка не связана с валидацией email");
    }

    @Test
    public void userEmailCantExistWithoutEmailSymbol() {
        User user = new User("LoginMail", "login", LocalDate.of(2000, 1, 1));
        Errors errors = new BeanPropertyBindingResult(user, "user");

        validator.validate(user, errors);
        assertTrue(errors.hasErrors(), "Валидация email не происходит");
        assertEquals(1, errors.getErrorCount(), "Количество ошибок не соответствует ожидаемому");
        assertNotNull(errors.getFieldError("email"), "Ошибка не связана с валидацией email");
    }

    @Test
    public void userLoginCantBeNull() {
        User user = new User("Login@Mail", null, LocalDate.of(2000, 1, 1));
        Errors errors = new BeanPropertyBindingResult(user, "user");

        validator.validate(user, errors);
        assertTrue(errors.hasErrors(), "Валидация login не происходит");
        assertEquals(1, errors.getErrorCount(), "Количество ошибок не соответствует ожидаемому");
        assertNotNull(errors.getFieldError("login"), "Ошибка не связана с валидацией login");
    }

    @Test
    public void userBirthdayCantBeInFuture() {
        User user = new User("Login@Mail", "Login", LocalDate.now().plusDays(1));
        Errors errors = new BeanPropertyBindingResult(user, "user");

        validator.validate(user, errors);
        assertTrue(errors.hasErrors(), "Валидация birthday не происходит");
        assertEquals(1, errors.getErrorCount(), "Количество ошибок не соответствует ожидаемому");
        assertNotNull(errors.getFieldError("birthday"), "Ошибка не связана с валидацией birthday");
    }
}
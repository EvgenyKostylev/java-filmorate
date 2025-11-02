package ru.yandex.practicum.filmorate.model;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.yandex.practicum.filmorate.dto.user.NewUserRequest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserTest {
    @Autowired
    private Validator validator;

    @Test
    public void userLoginCantExistWithWhitespaces() {
        NewUserRequest user = new NewUserRequest("Login@Mail",
                "Not Validate Login",
                "Name",
                LocalDate.of(2000, 1,
                        1));
        Errors errors = new BeanPropertyBindingResult(user, "user");

        validator.validate(user, errors);
        assertTrue(errors.hasErrors(), "Валидация login не происходит");
        assertEquals(1, errors.getErrorCount(), "Количество ошибок не соответствует ожидаемому");
        assertNotNull(errors.getFieldError("login"), "Ошибка не связана с валидацией login");
    }

    @Test
    public void userEmailCantBeNull() {
        NewUserRequest user = new NewUserRequest(null,
                "Login",
                "Name",
                LocalDate.of(2000, 1, 1));
        Errors errors = new BeanPropertyBindingResult(user, "user");

        validator.validate(user, errors);
        assertTrue(errors.hasErrors(), "Валидация email не происходит");
        assertEquals(1, errors.getErrorCount(), "Количество ошибок не соответствует ожидаемому");
        assertNotNull(errors.getFieldError("email"), "Ошибка не связана с валидацией email");
    }

    @Test
    public void userEmailCantExistWithoutEmailSymbol() {
        NewUserRequest user = new NewUserRequest("LoginMail",
                "Login",
                "Name",
                LocalDate.of(2000, 1, 1));
        Errors errors = new BeanPropertyBindingResult(user, "user");

        validator.validate(user, errors);
        assertTrue(errors.hasErrors(), "Валидация email не происходит");
        assertEquals(1, errors.getErrorCount(), "Количество ошибок не соответствует ожидаемому");
        assertNotNull(errors.getFieldError("email"), "Ошибка не связана с валидацией email");
    }

    @Test
    public void userLoginCantBeNull() {
        NewUserRequest user = new NewUserRequest("Login@Mail",
                null,
                "Name",
                LocalDate.of(2000, 1, 1));
        Errors errors = new BeanPropertyBindingResult(user, "user");

        validator.validate(user, errors);
        assertTrue(errors.hasErrors(), "Валидация login не происходит");
        assertEquals(1, errors.getErrorCount(), "Количество ошибок не соответствует ожидаемому");
        assertNotNull(errors.getFieldError("login"), "Ошибка не связана с валидацией login");
    }

    @Test
    public void userBirthdayCantBeInFuture() {
        NewUserRequest user = new NewUserRequest("Login@Mail",
                "Login",
                "Name",
                LocalDate.now().plusDays(1));
        Errors errors = new BeanPropertyBindingResult(user, "user");

        validator.validate(user, errors);
        assertTrue(errors.hasErrors(), "Валидация birthday не происходит");
        assertEquals(1, errors.getErrorCount(), "Количество ошибок не соответствует ожидаемому");
        assertNotNull(errors.getFieldError("birthday"), "Ошибка не связана с валидацией birthday");
    }
}
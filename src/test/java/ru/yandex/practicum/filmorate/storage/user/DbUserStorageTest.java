package ru.yandex.practicum.filmorate.storage.user;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.mapper.UserRowMapper;

import java.util.List;
import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({DbUserStorage.class,
        UserRowMapper.class})
class DbUserStorageTest {
    private final DbUserStorage dbUserStorage;

    @Test
    void testCreateUser() {
        User created = dbUserStorage.save(User.builder()
                .email("user@mail.com")
                .login("user").name("User")
                .birthday(LocalDate.of(1990, 1, 1))
                .build());

        assertThat(created.getId()).isPositive();

        Optional<User> loaded = dbUserStorage.get(created.getId());

        assertThat(loaded).isPresent();
        assertThat(loaded.get().getEmail()).isEqualTo("user@mail.com");
    }

    @Test
    void testFindUserById() {
        User created = dbUserStorage.save(User.builder()
                .email("user@mail.com")
                .login("user").name("User")
                .birthday(LocalDate.of(1990, 1, 1))
                .build());

        Optional<User> loaded = dbUserStorage.get(created.getId());

        assertThat(loaded).isPresent()
                .get()
                .hasFieldOrPropertyWithValue("email", "user@mail.com")
                .hasFieldOrPropertyWithValue("login", "user");
    }

    @Test
    void testUpdateUser() {
        User user = User.builder()
                .email("olduser@mail.com")
                .login("oldlogin").name("old name")
                .birthday(LocalDate.of(1980, 3, 10))
                .build();

        User created = dbUserStorage.save(user);

        created.setEmail("newuser@mail.com");
        created.setLogin("newlogin");
        created.setName("new name");
        dbUserStorage.update(created);

        Optional<User> updated = dbUserStorage.get(created.getId());

        assertThat(updated).isPresent();
        assertThat(updated.get().getEmail()).isEqualTo("newuser@mail.com");
        assertThat(updated.get().getName()).isEqualTo("new name");
    }

    @Test
    void testGetAllUsers() {
        dbUserStorage.save(User.builder()
                .email("user1@mail.com")
                .login("user1")
                .name("user one")
                .birthday(LocalDate.of(1990, 1, 1))
                .build());
        dbUserStorage.save(User.builder()
                .email("user2@mail.com")
                .login("user2")
                .name("user two")
                .birthday(LocalDate.of(1992, 2, 2))
                .build());

        List<User> users = dbUserStorage.getCollection();

        assertThat(users).isNotEmpty();
        assertThat(users.size()).isGreaterThanOrEqualTo(2);
    }

    @Test
    void testGetUserNotFound() {
        Optional<User> missing = dbUserStorage.get(9999L);

        assertThat(missing).isEmpty();
    }
}
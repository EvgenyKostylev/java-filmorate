package ru.yandex.practicum.filmorate.storage.friendship;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.FriendStatus;
import ru.yandex.practicum.filmorate.model.Friendship;
import ru.yandex.practicum.filmorate.model.User;
import ru.yandex.practicum.filmorate.storage.mapper.FriendshipRowMapper;
import ru.yandex.practicum.filmorate.storage.mapper.UserRowMapper;
import ru.yandex.practicum.filmorate.storage.user.DbUserStorage;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({DbFriendshipStorage.class,
        FriendshipRowMapper.class,
        DbUserStorage.class,
        UserRowMapper.class})
class DbFriendshipStorageTest {
    private final DbFriendshipStorage dbFriendshipStorage;
    private final DbUserStorage dbUserStorage;

    @Test
    void testSendFriendRequest() {
        User user1 = dbUserStorage.save(User.builder()
                .email("user1@mail.com")
                .login("user1")
                .name("user one")
                .birthday(LocalDate.of(1990, 1, 1))
                .build());

        User user2 = dbUserStorage.save(User.builder()
                .email("user2@mail.com")
                .login("user2")
                .name("user two")
                .birthday(LocalDate.of(1992, 2, 2))
                .build());

        dbFriendshipStorage.save(user1.getId(), user2.getId());

        Optional<Friendship> friendship = dbFriendshipStorage.get(user1.getId(), user2.getId());

        assertThat(friendship).isPresent();
        assertThat(friendship.get().getFirstUserId()).isEqualTo(user1.getId());
        assertThat(friendship.get().getSecondUserId()).isEqualTo(user2.getId());
        assertThat(friendship.get().getStatus()).isEqualTo(FriendStatus.PENDING);
    }

    @Test
    void testAcceptFriendship() {
        User user1 = dbUserStorage.save(User.builder()
                .email("user1@mail.com")
                .login("user1")
                .name("user one")
                .birthday(LocalDate.of(1990, 1, 1))
                .build());

        User user2 = dbUserStorage.save(User.builder()
                .email("user2@mail.com")
                .login("user2")
                .name("user two")
                .birthday(LocalDate.of(1992, 2, 2))
                .build());

        dbFriendshipStorage.save(user1.getId(), user2.getId());

        Optional<Friendship> pending = dbFriendshipStorage.get(user1.getId(), user2.getId());

        assertThat(pending).isPresent();
        dbFriendshipStorage.accept(pending.get().getId());

        Optional<Friendship> accepted = dbFriendshipStorage.get(user1.getId(), user2.getId());

        assertThat(accepted).isPresent();
        assertThat(accepted.get().getStatus()).isEqualTo(FriendStatus.CONFIRMED);
    }

    @Test
    void testRemoveFriendship() {
        User user1 = dbUserStorage.save(User.builder()
                .email("user1@mail.com")
                .login("user1")
                .name("user one")
                .birthday(LocalDate.of(1990, 1, 1))
                .build());

        User user2 = dbUserStorage.save(User.builder()
                .email("user2@mail.com")
                .login("user2")
                .name("user two")
                .birthday(LocalDate.of(1992, 2, 2))
                .build());

        dbFriendshipStorage.save(user1.getId(), user2.getId());

        Optional<Friendship> friendship = dbFriendshipStorage.get(user1.getId(), user2.getId());

        assertThat(friendship).isPresent();
        dbFriendshipStorage.delete(friendship.get().getId());

        Optional<Friendship> deleted = dbFriendshipStorage.get(user1.getId(), user2.getId());

        assertThat(deleted).isEmpty();
    }

    @Test
    void testGetAllFriendships() {
        User user1 = dbUserStorage.save(User.builder()
                .email("user1@mail.com")
                .login("user1")
                .name("user one")
                .birthday(LocalDate.of(1990, 1, 1))
                .build());

        User user2 = dbUserStorage.save(User.builder()
                .email("user2@mail.com")
                .login("user2")
                .name("user two")
                .birthday(LocalDate.of(1992, 2, 2))
                .build());

        User user3 = dbUserStorage.save(User.builder()
                .email("user3@mail.com")
                .login("user3")
                .name("user three")
                .birthday(LocalDate.of(1993, 3, 3))
                .build());

        dbFriendshipStorage.save(user1.getId(), user2.getId());
        dbFriendshipStorage.save(user1.getId(), user3.getId());

        List<Friendship> friendships = dbFriendshipStorage.getCollection(user1.getId());

        assertThat(friendships).isNotEmpty();
        assertThat(friendships.size()).isGreaterThanOrEqualTo(2);
    }
}
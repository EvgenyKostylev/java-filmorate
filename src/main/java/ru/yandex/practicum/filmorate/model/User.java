package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Data
public class User {
    private Long id;

    @NotBlank
    @Email
    private final String email;

    @NotBlank
    @Pattern(regexp = "^[^\\s]*$")
    private final String login;

    private String name;

    @PastOrPresent
    private final LocalDate birthday;

    private Set<Long> listFriendsId = new HashSet<>();

    public void addFriend(long friendId) {
        listFriendsId.add(friendId);
    }

    public void removeFriend(long friendId) {
        listFriendsId.remove(friendId);
    }

    public Collection<Long> getFriends() {
        return listFriendsId;
    }
}
package ru.yandex.practicum.filmorate.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Friendship {
    private long id;

    @Positive
    private Long firstUserId;

    @Positive
    private Long secondUserId;

    @NotEmpty
    private FriendStatus status;
}
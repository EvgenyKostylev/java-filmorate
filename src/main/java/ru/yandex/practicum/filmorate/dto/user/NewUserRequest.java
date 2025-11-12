package ru.yandex.practicum.filmorate.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class NewUserRequest {
    @NotBlank
    @Email
    private final String email;

    @NotBlank
    @Pattern(regexp = "^[^\\s]*$")
    private final String login;

    private final String name;

    @PastOrPresent
    private final LocalDate birthday;
}
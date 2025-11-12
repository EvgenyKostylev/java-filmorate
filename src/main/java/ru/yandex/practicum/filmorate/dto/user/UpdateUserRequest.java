package ru.yandex.practicum.filmorate.dto.user;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    @Positive
    private Long id;

    @Email
    private String email;

    @NotBlank
    @Pattern(regexp = "^[^\\s]*$")
    private String login;

    private String name;

    @PastOrPresent
    private LocalDate birthday;

    public boolean hasEmail() {
        return email != null && !email.isEmpty();
    }

    public boolean hasLogin() {
        return login != null && !login.isEmpty();
    }

    public boolean hasName() {
        return name != null && !name.isEmpty();
    }

    public boolean hasBirthday() {
        return birthday != null;
    }
}
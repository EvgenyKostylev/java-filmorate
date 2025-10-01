package ru.yandex.practicum.filmorate.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilmService {
    private final FilmStorage filmStorage;
    private final UserStorage userStorage;

    private static final int COUNT_FOR_MOST_POPULAR_LIST = 10;

    public void addLike(long filmId, long userId) {
        userStorage.get(userId);
        filmStorage.get(filmId).addLike(userId);
        log.info("Поставлен лайк на обьект фильма {} пользователем {}", filmId, userId);
    }

    public void removeLike(long filmId, long userId) {
        userStorage.get(userId);
        filmStorage.get(filmId).removeLike(userId);
        log.info("Убран лайк с обьекта фильма {} пользователем {}", filmId, userId);
    }

    public Collection<Film> getMostPopularFilms(long count) {
        var sortedFilms = filmStorage.getCollection()
                .stream()
                .filter(film -> !film.getListUsersByIdLikes().isEmpty())
                .sorted(Comparator.comparingLong(Film::getLikeCount).reversed())
                .toList();

        if (count > 0) {
            return sortedFilms.stream().limit(count).collect(Collectors.toList());
        }

        return sortedFilms.stream().limit(COUNT_FOR_MOST_POPULAR_LIST).collect(Collectors.toList());
    }
}
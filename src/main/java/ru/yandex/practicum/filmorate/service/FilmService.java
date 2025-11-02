package ru.yandex.practicum.filmorate.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.filmorate.dto.film.FilmDto;
import ru.yandex.practicum.filmorate.dto.film.NewFilmRequest;
import ru.yandex.practicum.filmorate.dto.film.UpdateFilmRequest;
import ru.yandex.practicum.filmorate.exception.NotFoundException;
import ru.yandex.practicum.filmorate.model.Film;
import ru.yandex.practicum.filmorate.model.FilmGenre;
import ru.yandex.practicum.filmorate.model.Genre;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.film.FilmStorage;
import ru.yandex.practicum.filmorate.storage.film.genre.FilmGenreStorage;
import ru.yandex.practicum.filmorate.storage.genre.GenreStorage;
import ru.yandex.practicum.filmorate.storage.like.LikeStorage;
import ru.yandex.practicum.filmorate.storage.mapper.FilmMapper;
import ru.yandex.practicum.filmorate.storage.rating.RatingStorage;
import ru.yandex.practicum.filmorate.storage.user.UserStorage;

import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FilmService {
    public FilmService(@Autowired @Qualifier("dbFilmStorage") FilmStorage filmStorage,
                       @Autowired @Qualifier("dbUserStorage") UserStorage userStorage,
                       @Autowired LikeStorage likeStorage,
                       @Autowired RatingStorage ratingStorage,
                       @Autowired FilmGenreStorage filmGenreStorage,
                       @Autowired GenreStorage genreStorage) {
        this.filmStorage = filmStorage;
        this.userStorage = userStorage;
        this.likeStorage = likeStorage;
        this.ratingStorage = ratingStorage;
        this.filmGenreStorage = filmGenreStorage;
        this.genreStorage = genreStorage;
    }

    private final FilmStorage filmStorage;
    private final UserStorage userStorage;
    private final LikeStorage likeStorage;
    private final RatingStorage ratingStorage;
    private final FilmGenreStorage filmGenreStorage;
    private final GenreStorage genreStorage;

    private static final int COUNT_FOR_MOST_POPULAR_LIST = 10;

    public FilmDto save(NewFilmRequest request) {
        Film film = FilmMapper.mapToFilm(request);

        film = filmStorage.save(film);

        List<Genre> genres = filmGenreStorage.saveCollection(film.getId(), request.getGenre())
                .stream()
                .map(genreId -> genreStorage.get(genreId).get())
                .toList();

        return FilmMapper.mapToFilmDto(film, ratingStorage.get(film.getRatingId()), genres);
    }

    public FilmDto update(UpdateFilmRequest request) {
        Film updatedFilm = filmStorage.get(request.getId())
                .map(film -> FilmMapper.updateFilmFields(film, request))
                .orElseThrow(() -> new NotFoundException("Фильм с id = " + request.getId() + " не найден"));

        updatedFilm = filmStorage.update(updatedFilm);

        List<Genre> genres = filmGenreStorage.saveCollection(updatedFilm.getId(), request.getGenre())
                .stream()
                .map(genreId -> genreStorage.get(genreId).get())
                .toList();

        return FilmMapper.mapToFilmDto(updatedFilm, ratingStorage.get(updatedFilm.getRatingId()), genres);
    }

    public FilmDto get(long filmId) {
        return filmStorage.get(filmId)
                .map(this::getFilmDto)
                .orElseThrow(() -> new NotFoundException("Фильм с id = " + filmId + " не найден"));
    }

    public Collection<FilmDto> getCollection() {
        return filmStorage.getCollection()
                .stream()
                .map(this::getFilmDto)
                .toList();
    }

    public void addLike(long filmId, long userId) {
        ensureUserExists(userId);
        ensureFilmExists(filmId);

        if (likeStorage.get(filmId, userId).isEmpty()) {
            likeStorage.save(filmId, userId);

            log.info("Поставлен лайк на обьект фильма {} пользователем {}", filmId, userId);
        } else {
            log.info("Лайк на фильм {} уже поставлен пользователем {}", filmId, userId);
        }
    }

    public void deleteLike(long filmId, long userId) {
        ensureUserExists(userId);
        ensureFilmExists(filmId);

        if (likeStorage.get(filmId, userId).isPresent()) {
            likeStorage.delete(filmId, userId);

            log.info("Убран лайк с обьекта фильма {} пользователем {}", filmId, userId);
        } else {
            log.info("Пользователь {} не ставил лайк на фильм {}", userId, filmId);
        }
    }

    public Collection<FilmDto> getMostPopularFilms(long count) {
        var sortedFilms = getCollection()
                .stream()
                .filter(filmDto -> !likeStorage.getCollection(filmDto.getId()).isEmpty())
                .sorted(Comparator
                        .comparingLong((FilmDto filmDto) -> likeStorage.getCollection(filmDto.getId()).size())
                        .reversed())
                .toList();

        if (count > 0) {
            return sortedFilms.stream().limit(count).collect(Collectors.toList());
        }

        return sortedFilms.stream().limit(COUNT_FOR_MOST_POPULAR_LIST).collect(Collectors.toList());
    }

    private FilmDto getFilmDto(Film film) {
        List<FilmGenre> filmGenres = filmGenreStorage.getCollection(film.getId());

        List<Genre> genres = filmGenres
                .stream()
                .filter(filmGenre -> filmGenre.getGenreId() != 0)
                .map(filmGenre -> genreStorage.get(filmGenre.getGenreId()).get())
                .toList();

        Optional<Rating> rating = ratingStorage.get(film.getRatingId());

        return FilmMapper.mapToFilmDto(film, rating, genres);
    }

    private void ensureUserExists(long userId) {
        if (userStorage.get(userId).isEmpty()) {
            throw new NotFoundException("Пользователь с id = " + userId + " не найден");
        }
    }

    private void ensureFilmExists(long filmId) {
        if (filmStorage.get(filmId).isEmpty()) {
            throw new NotFoundException("Фильм с id = " + filmId + " не найден");
        }
    }
}
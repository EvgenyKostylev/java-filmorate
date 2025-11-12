package ru.yandex.practicum.filmorate.storage.mapper;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.filmorate.model.FilmGenre;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmGenreRowMapper implements RowMapper<FilmGenre> {
    @Override
    public FilmGenre mapRow(ResultSet resultSet, int rowNum) throws SQLException {
        return FilmGenre.builder()
                .id(resultSet.getLong("id"))
                .filmId(resultSet.getLong("film_Id"))
                .genreId(resultSet.getLong("genre_id"))
                .build();
    }
}
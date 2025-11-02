package ru.yandex.practicum.filmorate.storage.rating;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.filmorate.model.Rating;
import ru.yandex.practicum.filmorate.storage.mapper.RatingRowMapper;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@JdbcTest
@AutoConfigureTestDatabase
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Import({DbRatingStorage.class,
        RatingRowMapper.class})
class DbRatingStorageTest {
    private final DbRatingStorage dbRatingStorage;

    @Test
    void testGetRatingById() {
        Optional<Rating> ratingOptional = dbRatingStorage.get(1L);

        assertThat(ratingOptional)
                .isPresent()
                .get()
                .hasFieldOrProperty("id")
                .hasFieldOrProperty("name");

        Rating rating = ratingOptional.get();

        assertThat(rating.getId()).isEqualTo(1L);
        assertThat(rating.getName()).isNotBlank();
    }

    @Test
    void testGetRatingNotFound() {
        Optional<Rating> ratingOptional = dbRatingStorage.get(9999L);

        assertThat(ratingOptional).isEmpty();
    }

    @Test
    void testGetAllRatings() {
        List<Rating> ratings = dbRatingStorage.getCollection();

        assertThat(ratings)
                .isNotNull()
                .isNotEmpty();
        assertThat(ratings)
                .allSatisfy(r -> {
                    assertThat(r.getId()).isPositive();
                    assertThat(r.getName()).isNotBlank();
                });
    }
}
DROP TABLE IF EXISTS film_genres;
DROP TABLE IF EXISTS likes;
DROP TABLE IF EXISTS friendships;
DROP TABLE IF EXISTS films;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS genres;
DROP TABLE IF EXISTS ratings;

CREATE TABLE IF NOT EXISTS users
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    email    varchar(255) NOT NULL,
    login    varchar(255) NOT NULL,
    name     varchar(255) NULL,
    birthday date         NOT NULL
);

CREATE TABLE IF NOT EXISTS friendships
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_user_id  BIGINT       NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    second_user_id BIGINT       NOT NULL REFERENCES users (id) ON DELETE CASCADE,
    status         varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS genres
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS ratings
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name varchar(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS films
(
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    name         varchar(255) NOT NULL,
    description  varchar(255) NOT NULL,
    release_date date         NOT NULL,
    duration     BIGINT       NOT NULL,
    rating       BIGINT REFERENCES ratings (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS likes
(
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    film_id BIGINT NOT NULL REFERENCES films (id) ON DELETE CASCADE,
    user_id BIGINT NOT NULL REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS film_genres
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    film_id  BIGINT REFERENCES films (id) ON DELETE CASCADE,
    genre_id BIGINT REFERENCES genres (id) ON DELETE CASCADE
);
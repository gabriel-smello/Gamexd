CREATE TABLE roles (
    role_id CHAR(36) PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

INSERT IGNORE INTO roles (role_id, name) VALUES (1, 'ADMIN');
INSERT IGNORE INTO roles (role_id, name) VALUES (2, 'BASIC');

--CREATE TABLE games (
--    id BIGINT PRIMARY KEY,
--    name VARCHAR(255) NOT NULL,
--    summary TEXT,
--    storyline TEXT,
--    release_date DATE,
--    total_rating DECIMAL(5,2),
--    rating_count INT,
--    cover_url VARCHAR(500),
--    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
--);
--
--CREATE TABLE genres (
--    id BIGINT PRIMARY KEY,
--    name VARCHAR(255) NOT NULL
--);
--
--CREATE TABLE platforms (
--    id BIGINT PRIMARY KEY,
--    name VARCHAR(255) NOT NULL
--);
--
--CREATE TABLE game_genres (
--    game_id BIGINT,
--    genre_id BIGINT,
--    PRIMARY KEY (game_id, genre_id),
--    FOREIGN KEY (game_id) REFERENCES games(id),
--    FOREIGN KEY (genre_id) REFERENCES genres(id)
--);
--
--CREATE TABLE game_platforms (
--    game_id BIGINT,
--    platform_id BIGINT,
--    PRIMARY KEY (game_id, platform_id),
--    FOREIGN KEY (game_id) REFERENCES games(id),
--    FOREIGN KEY (platform_id) REFERENCES platforms(id)
--);
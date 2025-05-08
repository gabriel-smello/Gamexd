USE igdb_cache;

SELECT * FROM game_lists;
SELECT * FROM game_list_games;
SELECT * FROM flyway_schema_history;
SELECT * FROM users;
SELECT * FROM roles;
SELECT * FROM users_roles;
SELECT * FROM games; 
SELECT * FROM games WHERE release_date > DATE_SUB(NOW(), INTERVAL 2 MONTH) ORDER BY total_rating DESC;
SELECT * FROM genres;
SELECT * FROM platforms;
SELECT * FROM game_platforms;
SELECT * FROM game_genres;
SELECT COUNT(*) FROM games;

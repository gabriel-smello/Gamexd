package com.gamexd.repository;

import com.gamexd.domain.entity.Games;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameRepository extends JpaRepository<Games, Long> {
    List<Games> findTop10GamesByOrderByTotalRatingDesc();

    @Query(value = "SELECT * FROM games WHERE release_date <= CURRENT_DATE ORDER BY release_date DESC LIMIT 10", nativeQuery = true)
    List<Games> findTop10RecentGames();

    @Query(value = "SELECT * FROM games WHERE release_date > DATE_SUB(NOW(), INTERVAL 2 MONTH) ORDER BY rating_count DESC", nativeQuery = true)
    List<Games> findTopTrendingGames(Pageable pageable);

    List<Games> findGamesByGenresId(Long genreId);
}

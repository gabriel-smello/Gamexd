package com.gamexd.repository;

import com.gamexd.domain.entity.Games;
import com.gamexd.domain.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUserIdAndGame(UUID userId, Games games);
}

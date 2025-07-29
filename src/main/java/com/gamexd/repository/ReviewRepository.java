package com.gamexd.repository;

import com.gamexd.domain.entity.Games;
import com.gamexd.domain.entity.Review;
import com.gamexd.domain.entity.User;
import com.gamexd.domain.enums.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Optional<Review> findByUserIdAndGame(UUID userId, Games games);

    List<Review> findAllByGame(Games game);

    List<Review> findAllByUser(User user);

    List<Review> getAllByUserId(UUID userId);

    List<Review> findByUserAndVisibility(User user, Visibility visibility);

    List<Review> findByGameAndVisibility(Games games, Visibility visibility);

}

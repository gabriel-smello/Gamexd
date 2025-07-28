package com.gamexd.service;

import com.gamexd.domain.dto.CreateReviewDto;
import com.gamexd.domain.dto.ReviewDto;
import com.gamexd.domain.entity.Games;
import com.gamexd.domain.entity.Review;
import com.gamexd.domain.entity.User;
import com.gamexd.mapper.ReviewMapper;
import com.gamexd.repository.GameRepository;
import com.gamexd.repository.ReviewRepository;
import com.gamexd.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.UUID;
@Service
public class ReviewService {
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewMapper reviewMapper;

    public ReviewDto createReview(CreateReviewDto dto, Long gameId, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Games game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado"));

        Review review = reviewRepository.findByUserIdAndGame(userId, game)
                .orElse(new Review());

        review.setUser(user);
        review.setRating(dto.getRating());
        review.setText(dto.getText());
        review.setGame(game);
        reviewRepository.save(review);

        updateGameRatingStats(game);

        return reviewMapper.toDto(review);
    }

    private void updateGameRatingStats(Games game) {
        var reviews = reviewRepository.findAllByGame(game);

        int count = reviews.size();
        double average = count > 0
                ? reviews.stream().mapToDouble(Review::getRating).average().orElse(0.0)
                : 0.0;

        game.setRatingCount(count);
        game.setTotalRating(average);

        gameRepository.save(game);
    }
}

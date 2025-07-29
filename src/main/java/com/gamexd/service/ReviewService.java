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

import java.util.List;
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

    public ReviewDto createOrUpdateReview(CreateReviewDto dto, Long gameId, Jwt jwt) {
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

    public List<ReviewDto> getReviewsByGame(Long gameId) {
        Games game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado"));

        return reviewMapper.toDtoList(reviewRepository.findAllByGame(game));
    }

    public List<ReviewDto> getReviewsByUser(UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User não encontrado"));

        return reviewMapper.toDtoList(reviewRepository.findAllByUser(user));
    }

    public List<ReviewDto> getReview(Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        String scopes = jwt.getClaimAsString("scope");

        if (scopes.contains("ADMIN")) {
            return reviewMapper.toDtoList(reviewRepository.findAll());
        }

        List<Review> reviews = reviewRepository.getAllByUserId(userId);

        return reviewMapper.toDtoList(reviews);
    }

    public ReviewDto getReviewById(Long reviewId, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        String scopes = jwt.getClaimAsString("scope");

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException("review não encontrada"));

        if (!review.getUser().getId().equals(userId) && !scopes.contains("ADMIN")) {
            throw new SecurityException("Você não tem permissão para acessar esta review");
        }

        return reviewMapper.toDto(review);
    }

    public void deleteReview(Long reviewId, Jwt jwt) {
        UUID userId = UUID.fromString(jwt.getSubject());
        String scopes = jwt.getClaimAsString("scope");

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException("Review não encontrada"));

        if (!review.getUser().getId().equals(userId) && !scopes.contains("ADMIN")) {
            throw new SecurityException("Você não tem permissão para deletar esta Review");
        }

        reviewRepository.delete(review);
    }
}

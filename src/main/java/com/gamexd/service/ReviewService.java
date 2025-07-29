package com.gamexd.service;

import com.gamexd.domain.dto.CreateReviewDto;
import com.gamexd.domain.dto.ReviewDto;
import com.gamexd.domain.entity.Games;
import com.gamexd.domain.entity.Review;
import com.gamexd.domain.entity.User;
import com.gamexd.domain.enums.Visibility;
import com.gamexd.mapper.ReviewMapper;
import com.gamexd.repository.GameRepository;
import com.gamexd.repository.ReviewRepository;
import com.gamexd.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        Long userId = Long.valueOf(jwt.getSubject());

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Games game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado"));

        Review review = reviewRepository.findByUserIdAndGame(userId, game)
                .orElse(new Review());

        review.setUser(user);
        review.setRating(dto.getRating());
        review.setText(dto.getText());
        review.setVisibility(dto.getVisibility());
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

    public List<ReviewDto> getReviewsByGame(Long gameId, Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        String scopes = jwt.getClaimAsString("scope");

        Games game = gameRepository.findById(gameId)
                .orElseThrow(() -> new EntityNotFoundException("Jogo não encontrado"));


        if (scopes.contains("ADMIN")) {
            return reviewMapper.toDtoList(reviewRepository.findAllByGame(game));
        }

        List<Review> reviews = reviewRepository.findAllByGame(game).stream()
                .filter(review ->
                        review.getVisibility() == Visibility.PUBLIC ||
                                review.getUser().getId().equals(userId)
                )
                .collect(Collectors.toList());

        return reviewMapper.toDtoList(reviews);
    }

    public List<ReviewDto> getReviewsByUserId(Long userId, Jwt jwt) {
        String scopes = jwt.getClaimAsString("scope");

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User não encontrado"));

        if (Long.valueOf(jwt.getSubject()).equals(userId) || scopes.contains("ADMIN")){
            return reviewMapper.toDtoList(reviewRepository.findAllByUser(user));
        }

        List<Review> reviews = reviewRepository.findByUserAndVisibility(user, Visibility.PUBLIC);
        return reviewMapper.toDtoList(reviews);
    }

    public List<ReviewDto> getReview(Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        String scopes = jwt.getClaimAsString("scope");

        if (scopes.contains("ADMIN")) {
            return reviewMapper.toDtoList(reviewRepository.findAll());
        }

        List<Review> reviews = reviewRepository.getAllByUserId(userId);

        return reviewMapper.toDtoList(reviews);
    }

    public ReviewDto getReviewById(Long reviewId, Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        String scopes = jwt.getClaimAsString("scope");

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException("review não encontrada"));

        if (!review.getUser().getId().equals(userId) && !scopes.contains("ADMIN") && review.getVisibility() == Visibility.PRIVATE) {
            throw new SecurityException("Você não tem permissão para acessar esta review");
        }

        return reviewMapper.toDto(review);
    }

    public void deleteReview(Long reviewId, Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        String scopes = jwt.getClaimAsString("scope");

        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new EntityNotFoundException("Review não encontrada"));

        if (!review.getUser().getId().equals(userId) && !scopes.contains("ADMIN")) {
            throw new SecurityException("Você não tem permissão para deletar esta Review");
        }

        Games game = review.getGame();
        reviewRepository.delete(review);
        updateGameRatingStats(game);
    }
}

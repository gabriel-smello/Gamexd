package com.gamexd.controller;

import com.gamexd.domain.dto.CreateReviewDto;
import com.gamexd.domain.dto.ReviewDto;
import com.gamexd.service.ReviewService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @PostMapping("/games/{gameId}")
    public ResponseEntity<ReviewDto> createOrUpdateReview(
            @PathVariable Long gameId,
            @RequestBody @Valid CreateReviewDto dto,
            @AuthenticationPrincipal Jwt jwt
    ) {
        return ResponseEntity.ok(reviewService.createOrUpdateReview(dto, gameId, jwt));
    }

    @GetMapping("/games/{gameId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByGame(@PathVariable Long gameId, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(reviewService.getReviewsByGame(gameId, jwt));
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<ReviewDto>> getReviewsByUser(@PathVariable Long userId, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId, jwt));
    }

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getReview(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(reviewService.getReview(jwt));
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable Long reviewId, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(reviewService.getReviewById(reviewId, jwt));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long reviewId, @AuthenticationPrincipal Jwt jwt) {
        reviewService.deleteReview(reviewId, jwt);
        return ResponseEntity.noContent().build();
    }
}

package com.gamexd.controller;

import com.gamexd.domain.dto.CommentDto;
import com.gamexd.domain.dto.CreateCommentDto;
import com.gamexd.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
public class CommentController {
    @Autowired
    private CommentService commentService;

    @PostMapping("/review/{reviewId}")
    public ResponseEntity<CommentDto> createCommentForReview(@PathVariable Long reviewId,
                                                             @RequestBody CreateCommentDto dto,
                                                             @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(commentService.createCommentForReview(reviewId, dto, jwt));
    }
}

package com.gamexd.service;

import com.gamexd.domain.dto.CommentDto;
import com.gamexd.domain.dto.CreateCommentDto;
import com.gamexd.domain.entity.Comment;
import com.gamexd.domain.entity.GameList;
import com.gamexd.domain.entity.Review;
import com.gamexd.domain.entity.User;
import com.gamexd.mapper.CommentMapper;
import com.gamexd.repository.CommentRepository;
import com.gamexd.repository.GameListRepository;
import com.gamexd.repository.ReviewRepository;
import com.gamexd.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private GameListRepository gameListRepository;
    public CommentDto createCommentForReview(Long reviewId, CreateCommentDto dto, Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new EntityNotFoundException("Review não encontrada"));

        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setAuthor(user);
        comment.setReview(review);

        return commentMapper.toDto(commentRepository.save(comment));
    }

    public CommentDto createCommentForGameList(Long gameListId, CreateCommentDto dto, Jwt jwt) {
        Long userId = Long.valueOf(jwt.getSubject());
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        GameList gameList = gameListRepository.findById(gameListId)
                .orElseThrow(() -> new EntityNotFoundException("Game List não encontrada"));

        Comment comment = new Comment();
        comment.setText(dto.getText());
        comment.setAuthor(user);
        comment.setGameList(gameList);

        return commentMapper.toDto(commentRepository.save(comment));
    }
}

package com.gamexd.repository;

import com.gamexd.domain.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByReviewId (Long reviewId);
    List<Comment> findByGameListId (Long gameListId);
}

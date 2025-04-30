package com.gamexd.repository;

import com.gamexd.domain.entity.Genres;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genres, Long> {
}

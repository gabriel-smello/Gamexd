package com.gamexd.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Game {
    @Id
    private Long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String summary;
    private LocalDate releaseDate;
    private Float rating;
    private String coverUrl;
    private String genreName;
    private Double trendingScore;
    private LocalDateTime updatedAt;
}

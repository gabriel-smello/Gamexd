package com.gamexd.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

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
    private Double trendingScore;
    private LocalDateTime updatedAt;
    @ManyToMany
    @JoinTable(name = "game_genre",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genre> genres;
}

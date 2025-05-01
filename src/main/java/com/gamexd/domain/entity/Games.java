package com.gamexd.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Games {
    @Id
    private Long id;
    private String name;
    @Column(columnDefinition = "TEXT")
    private String summary;
    private String storyline;
    @Column(name = "release_date")
    private LocalDate releaseDate;
    @Column(name = "total_rating")
    private Double totalRating;
    @Column(name = "rating_count")
    private Integer ratingCount;
    private String cover_url;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
    @ManyToMany
    @JoinTable(name = "game_genres",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "genre_id"))
    private Set<Genres> genres;
    @ManyToMany
    @JoinTable(name = "game_platforms",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "platform_id"))
    private Set<Platforms> platforms;
}

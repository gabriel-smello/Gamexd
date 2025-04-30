package com.gamexd.domain.dto;

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
public class GameDto {
    private Long id;
    private String name;
    private String summary;
    private String storyline;
    private LocalDate release_date;
    private Float totalRating;
    private Integer ratingCount;
    private String cover_url;
    private Set<GenreDto> genres;
    private Set<PlatformDto> platforms;
    private LocalDateTime created_at;
    private LocalDateTime updated_at;
}
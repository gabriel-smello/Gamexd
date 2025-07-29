package com.gamexd.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("release_date")
    private LocalDate releaseDate;
    @JsonProperty("total_rating")
    private Double totalRating;
    @JsonProperty("rating_count")
    private Integer ratingCount;
    private String cover_url;
    private Set<GenreDto> genres;
    private Set<PlatformDto> platforms;
}
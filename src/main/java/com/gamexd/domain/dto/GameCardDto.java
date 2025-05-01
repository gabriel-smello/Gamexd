package com.gamexd.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameCardDto {
    private Long id;
    private String name;
    private Set<GenreDto> genres;
    @JsonProperty("total_rating")
    private Double totalRating;
    private String cover_url;
}

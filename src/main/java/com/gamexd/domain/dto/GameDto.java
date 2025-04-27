package com.gamexd.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameDto {
    private Long id;
    private String name;
    private String summary;
    private Float rating;
    private Long first_release_date;
    private CoverDto cover;
    private Set<GenreDto> genres;
    private Double popularity;
    private Integer follows;
    private Integer hypes;
}
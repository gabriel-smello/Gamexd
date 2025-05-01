package com.gamexd.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameFilterDto {
    private String name;
    private LocalDate releaseDateFrom;
    private LocalDate releaseDateTo;
    private Double minRating;
    private Double maxRating;
    private Set<Long> genreIds;
    private Set<Long> platformIds;
}

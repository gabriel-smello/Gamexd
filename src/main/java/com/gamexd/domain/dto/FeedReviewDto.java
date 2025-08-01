package com.gamexd.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FeedReviewDto extends FeedDto{
    private Long reviewId;
    private Long gameId;
    private String gameName;
    private Double rating;
    private String text;
}

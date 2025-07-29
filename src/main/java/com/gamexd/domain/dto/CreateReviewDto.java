package com.gamexd.domain.dto;

import com.gamexd.domain.enums.Visibility;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateReviewDto {
    @NotNull
    @DecimalMin("0.0")
    @DecimalMax("5.0")
    private Double rating;
    private String text;
    @NotNull
    private Visibility visibility;
}

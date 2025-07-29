package com.gamexd.domain.dto;

import com.gamexd.domain.enums.Visibility;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {
    private Long id;
    private Double rating;
    private String text;
    @NotNull
    private Visibility visibility;
    private UUID userId;
    private Long gameId;
    private LocalDateTime createdAt;
}

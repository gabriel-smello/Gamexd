package com.gamexd.domain.dto;

import com.gamexd.domain.enums.Visibility;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameListDto {
    private Long id;
    @NotNull
    private String name;
    private String description;
    @NotNull
    private Visibility visibility;
    private Long userId;
    private Set<GameCardDto> games;
    private LocalDateTime createdAt;
}

package com.gamexd.domain.dto;

import com.gamexd.domain.enums.Visibility;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameListCreateDto {
    @NotNull
    private String name;
    private String description;
    @NotNull
    private Visibility visibility;
    Set<Long> gameIds;
}

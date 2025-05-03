package com.gamexd.domain.dto;

import com.gamexd.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;
import java.util.UUID;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GameListDto {
    private UUID id;
    private String name;
    private String description;
    private UUID userId;
    private Set<GameCardDto> games;
}

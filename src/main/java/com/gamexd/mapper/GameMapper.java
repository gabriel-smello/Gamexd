package com.gamexd.mapper;

import com.gamexd.domain.dto.GameCardDto;
import com.gamexd.domain.dto.GameDto;
import com.gamexd.domain.entity.Games;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper {
    GameDto toDto(Games games);

    List<GameDto> toDtoList(List<Games> games);

    List<GameCardDto> toGameCardDtoList(List<Games> games);

    Games toEntity(GameDto gameDto);
}

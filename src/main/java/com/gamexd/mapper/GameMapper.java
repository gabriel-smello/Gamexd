package com.gamexd.mapper;

import com.gamexd.domain.dto.GameDto;
import com.gamexd.domain.entity.Games;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GameMapper {
    GameDto toDto(Games game);

    List<GameDto> toDtoList(List<Games> games);

    Games toEntity(GameDto gameDto);
}

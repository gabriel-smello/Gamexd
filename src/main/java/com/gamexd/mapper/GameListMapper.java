package com.gamexd.mapper;

import com.gamexd.domain.dto.GameListDto;
import com.gamexd.domain.entity.GameList;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")

public interface GameListMapper {
    @Mapping(source = "user.id", target = "userId")
    GameListDto toDto(GameList gameList);

    List<GameListDto> toDtoList(List<GameList> gameList);
}

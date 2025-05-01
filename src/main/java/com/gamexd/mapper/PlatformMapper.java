package com.gamexd.mapper;

import com.gamexd.domain.dto.PlatformDto;
import com.gamexd.domain.entity.Platforms;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PlatformMapper {
    PlatformDto toDto(Platforms platforms);

    List<PlatformDto> toDtoList(List<Platforms> platforms);

    Platforms toEntity(PlatformDto platformDto);
}

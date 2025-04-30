package com.gamexd.mapper;

import com.gamexd.domain.dto.PlatformDto;
import com.gamexd.domain.entity.Platforms;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PlatformMapper {
    PlatformDto toDto(Platforms platforms);

    Platforms toEntity(PlatformDto platformDto);
}

package com.gamexd.mapper;

import com.gamexd.domain.dto.GenreDto;
import com.gamexd.domain.entity.Genres;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDto toDto(Genres genre);

    Genres toEntity(GenreDto genreDto);
}

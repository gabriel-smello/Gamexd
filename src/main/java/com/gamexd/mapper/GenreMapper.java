package com.gamexd.mapper;

import com.gamexd.domain.dto.GenreDto;
import com.gamexd.domain.entity.Genres;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    GenreDto toDto(Genres genre);

    List<GenreDto> toDtoList(List<Genres> genres);

    Genres toEntity(GenreDto genreDto);
}

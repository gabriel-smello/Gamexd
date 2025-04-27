package com.gamexd.mapper;

import com.gamexd.domain.dto.GenreDto;
import com.gamexd.domain.entity.Genre;

public class GenreMapper {
    public static Genre toEntity(GenreDto dto) {
        if (dto == null) return null;

        Genre genre = new Genre();
        genre.setId(dto.getId());
        genre.setName(dto.getName());
        return genre;
    }
}

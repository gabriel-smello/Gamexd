package com.gamexd.service;

import com.gamexd.domain.dto.GenreDto;
import com.gamexd.domain.entity.Genres;
import com.gamexd.mapper.GenreMapper;
import com.gamexd.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {
    @Autowired
    GenreRepository genreRepository;
    @Autowired
    GenreMapper genreMapper;

    public List<GenreDto> findAllGenres() {
        List<Genres> genres = genreRepository.findAll();
        return genreMapper.toDtoList(genres);
    }
}

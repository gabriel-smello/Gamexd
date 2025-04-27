package com.gamexd.mapper;

import com.gamexd.domain.dto.GameDto;
import com.gamexd.domain.entity.Game;
import com.gamexd.domain.entity.Genre;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class GameMapper {

    public Game toEntity(GameDto dto) {
        if (dto == null) {
            return null;
        }

        Game game = new Game();
        game.setId(dto.getId());
        game.setName(dto.getName());
        game.setSummary(dto.getSummary());
        game.setRating(dto.getRating());
        if (dto.getGenres() != null) {
            Set<Genre> genres = dto.getGenres().stream()
                    .map(GenreMapper::toEntity)
                    .collect(Collectors.toSet());
            game.setGenres(genres);
        }

        // Converte o timestamp de first_release_date para LocalDate
        if (dto.getFirst_release_date() != null) {
            game.setReleaseDate(LocalDate.ofInstant(Instant.ofEpochSecond(dto.getFirst_release_date()), java.time.ZoneId.systemDefault()));
        }

        // Converte a URL da capa
        if (dto.getCover() != null) {
            game.setCoverUrl(dto.getCover().getUrl());
        }

        // Atualiza a data de modificação (exemplo de uso da data atual)
        game.setUpdatedAt(LocalDate.now().atStartOfDay());

        return game;
    }
}

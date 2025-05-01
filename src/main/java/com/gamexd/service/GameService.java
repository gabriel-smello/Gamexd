package com.gamexd.service;

import com.gamexd.domain.dto.GameCardDto;
import com.gamexd.domain.dto.GameDto;
import com.gamexd.domain.entity.Games;
import com.gamexd.mapper.GameMapper;
import com.gamexd.repository.GameRepository;
import com.gamexd.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameMapper gameMapper;

    @Autowired
    private GenreRepository genreRepository;

    public GameDto getGameById(Long id) {
        Games gameEntity = gameRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Game not found with id: " + id));
        return gameMapper.toDto(gameEntity);
    }

    public List<GameCardDto> getTopTenGames() {
        List<Games> games = gameRepository.findTop10GamesByOrderByTotalRatingDesc();
        return gameMapper.toGameCardDtoList(games);
    }

    public List<GameCardDto> getNewlyGames() {
        List<Games> games = gameRepository.findTop10RecentGames();
        return gameMapper.toGameCardDtoList(games);
    }

    public List<GameCardDto> getTrendingGames() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Games> games = gameRepository.findTopTrendingGames(pageable);
        return gameMapper.toGameCardDtoList(games);
    }

    public List<GameCardDto> getGameByGenre(Long genreId) {
        List<Games> games = gameRepository.findGamesByGenresId(genreId);
        return gameMapper.toGameCardDtoList(games);
    }
}

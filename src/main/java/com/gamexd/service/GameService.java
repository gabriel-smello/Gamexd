package com.gamexd.service;

import com.gamexd.domain.entity.Game;
import com.gamexd.repository.GameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private IgdbService igdbService;

    public Game getGameById(Long id) {
        Optional<Game> local = gameRepository.findById(id);
        if (local.isPresent() && !isStale(local.get().getUpdatedAt())) {
            return local.get();
        }

        Game game = igdbService.fetchGameFromApi(id);
        game.setUpdatedAt(LocalDateTime.now());
        return gameRepository.save(game);
    }

    public List<Game> getTopTenGames() {
        List<Game> apiGames = igdbService.fetchTopTenGames();
        List<Game> result = new ArrayList<>();

        for (Game apiGame : apiGames) {
            Optional<Game> local = gameRepository.findById(apiGame.getId());

            if (local.isPresent() && !isStale(local.get().getUpdatedAt())) {
                result.add(local.get());
            } else {
                apiGame.setUpdatedAt(LocalDateTime.now());
                result.add(gameRepository.save(apiGame));
            }
        }

        return result;
    }

    public List<Game> getNewlyGames() {
        List<Game> apiGames = igdbService.fetchNewlyGames();
        List<Game> result = new ArrayList<>();

        for (Game apiGame : apiGames) {
            Optional<Game> local = gameRepository.findById(apiGame.getId());

            if (local.isPresent() && !isStale(local.get().getUpdatedAt())) {
                result.add(local.get());
            } else {
                apiGame.setUpdatedAt(LocalDateTime.now());
                result.add(gameRepository.save(apiGame));
            }
        }

        return result;
    }

    public List<Game> getTrendingGames() {
        List<Game> apiGames = igdbService.fetchTrendingGames();
        List<Game> result = new ArrayList<>();

        for (Game apiGame : apiGames) {
            Optional<Game> local = gameRepository.findById(apiGame.getId());

            if (local.isPresent() && !isStale(local.get().getUpdatedAt())) {
                result.add(local.get());
            } else {
                apiGame.setUpdatedAt(LocalDateTime.now());
                result.add(gameRepository.save(apiGame));
            }
        }

        return result;
    }

    private boolean isStale(LocalDateTime updatedAt) {
        return updatedAt == null || updatedAt.isBefore(LocalDateTime.now().minusDays(7));
    }
}

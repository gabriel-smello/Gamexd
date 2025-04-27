package com.gamexd.service;

import com.gamexd.domain.entity.Game;
import com.gamexd.domain.entity.Genre;
import com.gamexd.repository.GameRepository;
import com.gamexd.repository.GenreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private IgdbService igdbService;

    public Game getGameById(Long id) {
        Optional<Game> local = gameRepository.findById(id);
        if (local.isPresent() && !isStale(local.get().getUpdatedAt())) {
            return local.get();
        }

        Game game = igdbService.fetchGameFromApi(id);
        fetchGenre(game);

        game.setUpdatedAt(LocalDateTime.now());
        return gameRepository.save(game);
    }

    public List<Game> getTopTenGames() {
        List<Game> apiGames = igdbService.fetchTopTenGames();
        List<Game> result = new ArrayList<>();

        for (Game apiGame : apiGames) {
            fetchGenre(apiGame);
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
            fetchGenre(apiGame);
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
            fetchGenre(apiGame);
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

    private void fetchGenre(Game apiGame) {
        if (apiGame.getGenres() != null) {
            Set<Genre> managedGenres = apiGame.getGenres().stream()
                    .map(genre -> genreRepository.findById(genre.getId())
                            .orElseGet(() -> genreRepository.save(genre))) // se não achar, salva
                    .collect(Collectors.toSet());
            apiGame.setGenres(managedGenres);
        }
    }
}

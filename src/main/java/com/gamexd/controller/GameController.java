package com.gamexd.controller;

import com.gamexd.domain.entity.Game;
import com.gamexd.repository.GameRepository;
import com.gamexd.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;
    GameRepository gameRepository;


    @GetMapping("/card/{id}")
    public ResponseEntity<Game> getGame(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @GetMapping("/top-ten")
    public ResponseEntity<List<Game>> getTopTenGames() {
        return ResponseEntity.ok(gameService.getTopTenGames());
    }

    @GetMapping("/newly")
    public ResponseEntity<List<Game>> getNewlyGames() {
        return ResponseEntity.ok(gameService.getNewlyGames());
    }

    @GetMapping("/trending")
    public ResponseEntity<List<Game>> getTrendingGames() {
        return ResponseEntity.ok(gameService.getTrendingGames());
    }
}

package com.gamexd.controller;

import com.gamexd.domain.dto.GameCardDto;
import com.gamexd.domain.dto.GameDto;
import com.gamexd.domain.dto.GameFilterDto;
import com.gamexd.repository.GameRepository;
import com.gamexd.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/games")
public class GameController {

    @Autowired
    private GameService gameService;
    GameRepository gameRepository;

    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGameById(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getGameById(id));
    }

    @GetMapping("/top-ten")
    public ResponseEntity<List<GameCardDto>> getTopTenGames() {
        return ResponseEntity.ok(gameService.getTopTenGames());
    }

    @GetMapping("/newly")
    public ResponseEntity<List<GameCardDto>> getNewlyGames() {
        return ResponseEntity.ok(gameService.getNewlyGames());
    }

    @GetMapping("/trending")
    public ResponseEntity<List<GameCardDto>> getTrendingGames() {
        return ResponseEntity.ok(gameService.getTrendingGames());
    }

    @GetMapping
    public ResponseEntity<Page<GameCardDto>> getFilteredGames(@ModelAttribute GameFilterDto filter,
                                                              @PageableDefault(size = 50) Pageable pageable) {
        return ResponseEntity.ok(gameService.getFilteredGames(filter, pageable));
    }
}

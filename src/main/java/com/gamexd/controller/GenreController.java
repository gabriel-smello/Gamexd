package com.gamexd.controller;

import com.gamexd.domain.dto.GameCardDto;
import com.gamexd.domain.dto.GenreDto;
import com.gamexd.service.GameService;
import com.gamexd.service.GenreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/genres")
public class GenreController {
    @Autowired
    GenreService genreService;
    @Autowired
    GameService gameService;

    @GetMapping
    ResponseEntity<List<GenreDto>> findAllGenres() {
        return ResponseEntity.ok(genreService.findAllGenres());
    }

    @GetMapping("/{id}/games")
    ResponseEntity<List<GameCardDto>> getGamesByGenre(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getGameByGenre(id));
    }
}

package com.gamexd.controller;

import com.gamexd.domain.dto.GameCardDto;
import com.gamexd.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/platforms")
public class PlatformController {
    @Autowired
    GameService gameService;

    @GetMapping("/{id}/games")
    ResponseEntity<List<GameCardDto>> getGamesByPlarform(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getGameByPlatform(id));
    }
}

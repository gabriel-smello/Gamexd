package com.gamexd.controller;

import com.gamexd.domain.dto.GameCardDto;
import com.gamexd.domain.dto.GenreDto;
import com.gamexd.domain.dto.PlatformDto;
import com.gamexd.service.GameService;
import com.gamexd.service.PlatformService;
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
    PlatformService platformService;
    @Autowired
    GameService gameService;

    @GetMapping
    ResponseEntity<List<PlatformDto>> findAllPlatforms() {
        return ResponseEntity.ok(platformService.findAllPlatforms());
    }

    @GetMapping("/{id}/games")
    ResponseEntity<List<GameCardDto>> getGamesByPlarform(@PathVariable Long id) {
        return ResponseEntity.ok(gameService.getGameByPlatform(id));
    }
}

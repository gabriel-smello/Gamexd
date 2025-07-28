package com.gamexd.controller;

import com.gamexd.domain.dto.GameListCreateDto;
import com.gamexd.domain.dto.GameListDto;
import com.gamexd.service.GameListService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game-list")
public class GameListController {
    @Autowired
    GameListService gameListService;

    @PostMapping
    public ResponseEntity<GameListDto> createGameList(@RequestBody @Valid GameListCreateDto dto, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(gameListService.createList(dto, jwt));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GameListDto>> getGameListsByUser(@PathVariable UUID userId, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(gameListService.getGameListsByUserId(userId, jwt));
    }

    @GetMapping
    public ResponseEntity<List<GameListDto>> getGameList(@AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(gameListService.getGameList(jwt));
    }

    @GetMapping("/{listId}")
    public ResponseEntity<GameListDto> getGameListById(@PathVariable Long listId, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(gameListService.getGameListById(listId, jwt));
    }

    @PutMapping("/{listId}")
    public ResponseEntity<GameListDto> updateGameList(@PathVariable Long listId, @RequestBody @Valid GameListCreateDto dto, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(gameListService.updateGameList(listId, dto, jwt));
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteGameList(@PathVariable Long listId, @AuthenticationPrincipal Jwt jwt) {
        gameListService.deleteGameList(listId, jwt);
        return ResponseEntity.noContent().build();
    }
}

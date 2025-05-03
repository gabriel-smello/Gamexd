package com.gamexd.controller;

import com.gamexd.domain.dto.GameListCreateDto;
import com.gamexd.domain.dto.GameListDto;
import com.gamexd.service.GameListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/game-list")
public class GameListController {
    @Autowired
    GameListService gameListService;

    @PostMapping
    public ResponseEntity<GameListDto> createList(@RequestBody GameListCreateDto dto, @AuthenticationPrincipal String userId) {
        return ResponseEntity.ok(gameListService.createList(dto, UUID.fromString(userId)));
    }

    @GetMapping
    public ResponseEntity<List<GameListDto>> getAllLists() {
        return ResponseEntity.ok(gameListService.getAllLists());
    }
}

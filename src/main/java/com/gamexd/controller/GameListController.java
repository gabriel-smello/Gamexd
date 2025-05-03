package com.gamexd.controller;

import com.gamexd.domain.dto.GameListCreateDto;
import com.gamexd.domain.dto.GameListDto;
import com.gamexd.domain.entity.GameList;
import com.gamexd.service.GameListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}

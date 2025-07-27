package com.gamexd.controller;

import com.gamexd.domain.dto.GameListCreateDto;
import com.gamexd.domain.dto.GameListDto;
import com.gamexd.service.GameListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game-list")
public class GameListController {
    @Autowired
    GameListService gameListService;

    @PostMapping
    public ResponseEntity<GameListDto> createList(@RequestBody GameListCreateDto dto, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(gameListService.createList(dto, jwt));
    }

    @GetMapping
    public ResponseEntity<List<GameListDto>> getAllLists() {
        return ResponseEntity.ok(gameListService.getAllLists());
    }

    @GetMapping("/{listId}")
    public ResponseEntity<GameListDto> updateList(@PathVariable Long listId, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(gameListService.getList(listId, jwt));
    }

    @PutMapping("/{listId}")
    public ResponseEntity<GameListDto> updateList(@PathVariable Long listId, @RequestBody GameListCreateDto dto, @AuthenticationPrincipal Jwt jwt) {
        return ResponseEntity.ok(gameListService.updateList(listId, dto, jwt));
    }

    @DeleteMapping("/{listId}")
    public ResponseEntity<Void> deleteList(@PathVariable Long listId, @AuthenticationPrincipal Jwt jwt) {
        gameListService.deleteList(listId, jwt);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/teste")
    public ResponseEntity<?> teste(@AuthenticationPrincipal Jwt jwt) {
        String userId = jwt.getSubject();
        System.out.println(userId);
        String scopes = jwt.getClaimAsString("scope");
        System.out.println(scopes);
        return ResponseEntity.ok().build();
    }
}

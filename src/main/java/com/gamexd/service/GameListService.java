package com.gamexd.service;

import com.gamexd.domain.dto.GameListCreateDto;
import com.gamexd.domain.dto.GameListDto;
import com.gamexd.domain.entity.GameList;
import com.gamexd.domain.entity.Games;
import com.gamexd.domain.entity.User;
import com.gamexd.mapper.GameListMapper;
import com.gamexd.repository.GameListRepository;
import com.gamexd.repository.GameRepository;
import com.gamexd.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class GameListService {
    @Autowired
    private GameListRepository gameListRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private GameListMapper gameListMapper;

    public GameListDto createList(GameListCreateDto dto, UUID userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));
        GameList gameList = new GameList();

        gameList.setName(dto.getName());
        gameList.setDescription(dto.getDescription());
        gameList.setUser(user);
        if (dto.getGameIds() != null && !dto.getGameIds().isEmpty()) {
            Set<Games> games = new HashSet<>(gameRepository.findAllById(dto.getGameIds()));
            gameList.setGames(games);
        }
        return gameListMapper.toDto(gameListRepository.save(gameList));
    }
}

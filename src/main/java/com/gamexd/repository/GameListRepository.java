package com.gamexd.repository;

import com.gamexd.domain.entity.GameList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GameListRepository extends JpaRepository<GameList, Long> {
    List<GameList> getGameListsByUserId(UUID userId);
}

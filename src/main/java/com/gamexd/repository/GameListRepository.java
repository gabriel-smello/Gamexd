package com.gamexd.repository;

import com.gamexd.domain.entity.GameList;
import com.gamexd.domain.entity.User;
import com.gamexd.domain.enums.Visibility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface GameListRepository extends JpaRepository<GameList, Long> {
    List<GameList> getGameListsByUser(User user);

    List<GameList> getGameListsByUserId(Long userId);

    List<GameList> findByUserAndVisibility(User user, Visibility visibility);

    List<GameList> findByUserInAndVisibilityOrderByCreatedAtDesc(Set<User> users, Visibility visibility);
}

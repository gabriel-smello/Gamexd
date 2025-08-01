package com.gamexd.repository;

import com.gamexd.domain.entity.Feed;
import com.gamexd.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Set;

public interface FeedRepository extends JpaRepository<Feed, Long> {
    List<Feed> findByActorInOrderByTimestampDesc(Set<User> actors);
}

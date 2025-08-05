package com.gamexd.service;

import com.gamexd.domain.dto.FeedDto;
import com.gamexd.domain.entity.User;
import com.gamexd.mapper.FeedMapper;
import com.gamexd.repository.FeedRepository;
import com.gamexd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class FeedService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FeedRepository feedRepository;
    @Autowired
    private FeedMapper feedMapper;

    public List<FeedDto> getFeed(Jwt jwt) {
        User currentUser = userRepository.getReferenceById(Long.valueOf(jwt.getSubject()));
        Set<User> following = currentUser.getFollowing();

        return feedRepository.findByActorInOrderByTimestampDesc(following)
                .stream()
                .map(event -> switch (event.getType()) {
                    case REVIEW_CREATED, REVIEW_UPDATED -> feedMapper.toReviewDto(event);
                    case GAMELIST_CREATED, GAMELIST_UPDATED -> feedMapper.toGameListDto(event);
                })
                .toList();
    }
}

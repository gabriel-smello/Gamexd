package com.gamexd.service;

import com.gamexd.domain.dto.FeedDto;
import com.gamexd.domain.entity.User;
import com.gamexd.mapper.FeedMapper;
import com.gamexd.repository.FeedRepository;
import com.gamexd.repository.GameListRepository;
import com.gamexd.repository.ReviewRepository;
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
    private ReviewRepository reviewRepository;
    @Autowired
    private GameListRepository gameListRepository;
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

//    public List<FeedDto> getFeed(Jwt jwt) {
//        User currentUser = userRepository.getReferenceById(Long.valueOf(jwt.getSubject()));
//
//        Set<User> following = currentUser.getFollowing();
//        List<FeedDto> feed = new ArrayList<>();
//
//        List<Review> reviews = reviewRepository.findByUserInAndVisibilityOrderByCreatedAtDesc(following, Visibility.PUBLIC);
//        for (Review r : reviews) {
//            FeedDto item = new FeedDto();
//            item.setType("review");
//            item.setUsername(r.getUser().getEmail());
//            item.setDescription("avaliou " + r.getGame().getName() + " com " + r.getRating() + " estrelas");
//            item.setTimestamp(r.getCreatedAt());
//            feed.add(item);
//        }
//
//        List<GameList> lists = gameListRepository.findByUserInAndVisibilityOrderByCreatedAtDesc(following, Visibility.PUBLIC);
//        for (GameList list : lists) {
//            FeedDto item = new FeedDto();
//            item.setType("list");
//            item.setUsername(list.getUser().getEmail());
//            item.setDescription("criou a lista '" + list.getName() + "'");
//            item.setTimestamp(list.getCreatedAt());
//            feed.add(item);
//        }
//
//        feed.sort((a, b) -> b.getTimestamp().compareTo(a.getTimestamp()));
//
//        return feed;
//    }
}

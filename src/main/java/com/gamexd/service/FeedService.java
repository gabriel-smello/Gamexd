package com.gamexd.service;

import com.gamexd.domain.dto.FeedDto;
import com.gamexd.domain.entity.Feed;
import com.gamexd.domain.entity.GameList;
import com.gamexd.domain.entity.Review;
import com.gamexd.domain.entity.User;
import com.gamexd.domain.enums.Visibility;
import com.gamexd.repository.FeedRepository;
import com.gamexd.repository.GameListRepository;
import com.gamexd.repository.ReviewRepository;
import com.gamexd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public List<FeedDto> getFeed(Jwt jwt) {
        User currentUser = userRepository.getReferenceById(Long.valueOf(jwt.getSubject()));
        Set<User> following = currentUser.getFollowing();

        List<Feed> events = feedRepository.findByActorInOrderByTimestampDesc(following);
        List<FeedDto> feed = new ArrayList<>();

        for (Feed event : events) {
            FeedDto dto = new FeedDto();
            dto.setId(event.getId());
            dto.setTimestamp(event.getTimestamp());
            dto.setUsername(event.getActor().getEmail());
            switch (event.getType()) {
                case REVIEW_CREATED -> {
                    dto.setType("review");
                    dto.setDescription("avaliou " + event.getReview().getGame().getName() +
                            " com " + event.getReview().getRating() + " estrelas");
                }
                case REVIEW_UPDATED -> {
                    dto.setType("review");
                    dto.setDescription("atualizou a avaliação de " + event.getReview().getGame().getName() +
                            " para " + event.getReview().getRating() + " estrelas");
                }
                case GAMELIST_CREATED -> {
                    dto.setType("gameList");
                    dto.setDescription("criou a lista '" + event.getGameList().getName() + "'");
                }
                case GAMELIST_UPDATED -> {
                    dto.setType("gameList");
                    dto.setDescription("atualizou a lista '" + event.getGameList().getName() + "'");
                }
            }
            feed.add(dto);
        }
        return feed;
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

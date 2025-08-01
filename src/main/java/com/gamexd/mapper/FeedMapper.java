package com.gamexd.mapper;

import com.gamexd.domain.dto.FeedGameListDto;
import com.gamexd.domain.dto.FeedReviewDto;
import com.gamexd.domain.entity.Feed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FeedMapper {
    @Mapping(target = "type", constant = "review")
    @Mapping(source = "actor.email", target = "username")
    @Mapping(source = "review.id", target = "reviewId")
    @Mapping(source = "review.rating", target = "rating")
    @Mapping(source = "review.text", target = "text")
    @Mapping(source = "review.game.id", target = "gameId")
    @Mapping(source = "review.game.name", target = "gameName")
    FeedReviewDto toReviewDto(Feed feed);
    @Mapping(target = "type", constant = "gameList")
    @Mapping(source = "actor.email", target = "username")
    @Mapping(source = "gameList.id", target = "gameListId")
    @Mapping(source = "gameList.name", target = "gameListName")
    @Mapping(source = "gameList.description", target = "description")
    FeedGameListDto toGameListDto(Feed feed);
}

package com.gamexd.mapper;

import com.gamexd.domain.dto.FeedGameListDto;
import com.gamexd.domain.dto.FeedReviewDto;
import com.gamexd.domain.entity.Feed;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ReviewMapper.class, GameListMapper.class})
public interface FeedMapper {
    @Mapping(target = "type", constant = "REVIEW")
    @Mapping(source = "actor.email", target = "username")
    FeedReviewDto toReviewDto(Feed feed);
    @Mapping(target = "type", constant = "GAMELIST")
    @Mapping(source = "actor.email", target = "username")
    FeedGameListDto toGameListDto(Feed feed);
}

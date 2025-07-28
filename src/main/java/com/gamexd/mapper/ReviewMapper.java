package com.gamexd.mapper;

import com.gamexd.domain.dto.ReviewDto;
import com.gamexd.domain.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ReviewMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "game.id", target = "gameId")
    ReviewDto toDto(Review review);

    List<ReviewDto> toDtoList(List<Review> reviews);

    Review toEntity(ReviewDto reviewDto);
}

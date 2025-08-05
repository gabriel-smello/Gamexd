package com.gamexd.mapper;

import com.gamexd.domain.dto.CommentDto;
import com.gamexd.domain.entity.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    @Mapping(source = "author.id", target = "userId")
    CommentDto toDto(Comment comment);
}

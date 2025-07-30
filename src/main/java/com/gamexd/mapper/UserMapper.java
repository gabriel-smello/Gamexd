package com.gamexd.mapper;

import com.gamexd.domain.dto.UserDto;
import com.gamexd.domain.entity.User;
import org.mapstruct.Mapper;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring")

public interface UserMapper {
    List<UserDto> toDtoList(List<User> users);
    Set<UserDto> toDtoSet(Set<User> users);

}

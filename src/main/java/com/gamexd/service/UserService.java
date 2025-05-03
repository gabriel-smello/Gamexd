package com.gamexd.service;

import com.gamexd.domain.dto.UserDto;
import com.gamexd.mapper.UserMapper;
import com.gamexd.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    public List<UserDto> listUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }
}

package com.gamexd.service;

import com.gamexd.domain.dto.UserDto;
import com.gamexd.domain.entity.User;
import com.gamexd.mapper.UserMapper;
import com.gamexd.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    public List<UserDto> listUsers() {
        return userMapper.toDtoList(userRepository.findAll());
    }

    public void followUser(Long followedId, Jwt jwt) {
        User currentUser = userRepository.getReferenceById(Long.valueOf(jwt.getSubject()));
        if (currentUser.getId().equals(followedId)) {
            throw new IllegalArgumentException("Você não pode se seguir.");
        }

        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (currentUser.getFollowing().contains(followed)) {
            throw new IllegalStateException("Você já está seguindo este usuário.");
        }

        currentUser.getFollowing().add(followed);
        userRepository.save(currentUser);
    }

    public void unfollowUser(Long followedId, Jwt jwt) {
        User currentUser = userRepository.getReferenceById(Long.valueOf(jwt.getSubject()));
        if (currentUser.getId().equals(followedId)) {
            throw new IllegalArgumentException("Você não pode deixar de se seguir.");
        }

        User followed = userRepository.findById(followedId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        if (!currentUser.getFollowing().contains(followed)) {
            throw new IllegalStateException("Você não está seguindo este usuário.");
        }

        currentUser.getFollowing().remove(followed);
        userRepository.save(currentUser);
    }

    public Set<UserDto> getFollowers(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return userMapper.toDtoSet(user.getFollowers());
    }

    public Set<UserDto> getFollowing(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuário não encontrado"));

        return userMapper.toDtoSet(user.getFollowing());

    }
}

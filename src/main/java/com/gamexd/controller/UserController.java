package com.gamexd.controller;

import com.gamexd.domain.dto.UserDto;
import com.gamexd.repository.UserRepository;
import com.gamexd.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<UserDto>> listUsers() {
        return ResponseEntity.ok(userService.listUsers());
    }

    @PostMapping("/{userId}/follow")
    public ResponseEntity<Void> followUser(@PathVariable Long userId, @AuthenticationPrincipal Jwt jwt) {
        userService.followUser(userId, jwt);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/follow")
    public ResponseEntity<Void> unfollowUser(@PathVariable Long userId, @AuthenticationPrincipal Jwt jwt) {
        userService.unfollowUser(userId, jwt);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{userId}/followers")
    public ResponseEntity<Set<UserDto>> getFollowers(@PathVariable Long userId) {

        return ResponseEntity.ok(userService.getFollowers(userId));
    }

    @GetMapping("/{userId}/following")
    public ResponseEntity<Set<UserDto>> getFollowing(@PathVariable Long userId) {

        return ResponseEntity.ok(userService.getFollowing(userId));
    }
}

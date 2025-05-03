package com.gamexd.controller;

import com.gamexd.domain.dto.CreateUserDto;
import com.gamexd.domain.entity.Role;
import com.gamexd.domain.entity.User;
import com.gamexd.repository.RoleRepository;
import com.gamexd.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserController(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @PostMapping
    @Transactional
    public ResponseEntity<Void> newUser(@RequestBody CreateUserDto dto) {
        System.out.println("[REGISTER]: " + dto);
        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());

        var userFromDb = userRepository.findByUsername(dto.username());
        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        var user = new User();
        user.setUsername(dto.username());
        user.setPassword(bCryptPasswordEncoder.encode(dto.password()));
        user.setRole(Set.of(basicRole));

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @GetMapping()
    public ResponseEntity<List<User>> listUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

}

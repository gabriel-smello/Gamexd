package com.gamexd.controller;

import com.gamexd.domain.dto.CreateUserDto;
import com.gamexd.domain.dto.LoginRequest;
import com.gamexd.domain.dto.LoginResponse;
import com.gamexd.domain.entity.EmailValidation;
import com.gamexd.domain.entity.Role;
import com.gamexd.domain.entity.User;
import com.gamexd.repository.EmailValidationRepository;
import com.gamexd.repository.RoleRepository;
import com.gamexd.repository.UserRepository;
import com.gamexd.service.EmailValidationService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.time.Instant;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
public class TokenController {
    private final JwtEncoder jwtEncoder;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;
    private final EmailValidationRepository emailValidationRepository;
    private final EmailValidationService emailValidationService;


    public TokenController(JwtEncoder jwtEncoder, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository, EmailValidationRepository emailValidationRepository, EmailValidationService emailValidationService) {
        this.jwtEncoder = jwtEncoder;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
        this.emailValidationRepository = emailValidationRepository;
        this.emailValidationService = emailValidationService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var user = userRepository.findByEmail(loginRequest.email());

        if (user.isEmpty()) {
            throw new BadCredentialsException("Invalid email or password.");
        }

        if (!user.get().isEnabled()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User has not confirmed their email.");
        }

        if (!user.get().isLoginCorrect(loginRequest, bCryptPasswordEncoder)) {
            throw new BadCredentialsException("Invalid email or password.");
        }

        var now = Instant.now();
        var expiresIn = 300L;
        var scopes = user.get().getRole()
                .stream()
                .map(Role::getName)
                .collect(Collectors.joining(" "));

        var claims = JwtClaimsSet.builder()
                .issuer("Gamexd")
                .subject(user.get().getId().toString())
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expiresIn))
                .claim("scope", scopes)
                .build();
        var jwtValue = jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
        return ResponseEntity.ok(new LoginResponse(jwtValue, expiresIn));
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<Void> newUser(@RequestBody @Valid CreateUserDto dto) {
        var basicRole = roleRepository.findByName(Role.Values.BASIC.name());

        var userFromDb = userRepository.findByEmail(dto.email());
        if (userFromDb.isPresent()) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY);
        }
        var user = new User();
        user.setEmail(dto.email());
        user.setPassword(bCryptPasswordEncoder.encode(dto.password()));
        user.setRole(Set.of(basicRole));
        user.setEnabled(false);

        userRepository.save(user);

        /* CRIAÇÃO EMAIL VALIDATION*/
        var token = UUID.randomUUID().toString();
        var emailValidation = new EmailValidation();
        emailValidation.setToken(token);
        emailValidation.setUser(user);
        emailValidation.setExpiresAt(Instant.now().plus(Duration.ofHours(24)));
        emailValidationRepository.save(emailValidation);
        emailValidationService.sendConfirmationEmail(user.getEmail(), token);

        return ResponseEntity.ok().build();
    }
@GetMapping("/validate")
    public ResponseEntity<String> confirmEmail(@RequestParam String token) {
        var optionalToken = emailValidationRepository.findByToken(token);
        if (optionalToken.isEmpty()) {
            return ResponseEntity.badRequest().body("Token inválido.");
        }

        var emailToken = optionalToken.get();
        if (emailToken.getExpiresAt().isBefore(Instant.now())) {
            return ResponseEntity.status(HttpStatus.GONE).body("Token expirado.");
        }

        var user = emailToken.getUser();
        user.setEnabled(true);
        userRepository.save(user);

        emailValidationRepository.delete(emailToken);

        return ResponseEntity.ok("E-mail is now valid!");
    }
}

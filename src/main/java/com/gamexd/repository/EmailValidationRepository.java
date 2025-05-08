package com.gamexd.repository;

import com.gamexd.domain.entity.EmailValidation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EmailValidationRepository extends JpaRepository<EmailValidation, Long> {
    Optional<EmailValidation> findByToken(String token);
}

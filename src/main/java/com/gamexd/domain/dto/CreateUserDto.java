package com.gamexd.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserDto(
        @NotBlank
        @Email(message = "E-mail invalid.")
        String email,

        @NotBlank
        String password
) {
}

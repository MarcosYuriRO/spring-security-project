package com.marcos.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateUserDto(@NotBlank @NotNull String username, @NotBlank @NotNull String password) {

}

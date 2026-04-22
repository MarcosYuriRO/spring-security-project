package com.marcos.security.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateTweetDto(@NotBlank @NotNull String content) {

}

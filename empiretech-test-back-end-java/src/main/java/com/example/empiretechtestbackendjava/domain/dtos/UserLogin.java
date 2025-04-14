package com.example.empiretechtestbackendjava.domain.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserLogin(@NotBlank String username, @NotBlank String password) {}

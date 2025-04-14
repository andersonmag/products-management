package com.example.empiretechtestbackendjava.domain.dtos;

public record TenantResponse(String name, String domain, String urlDatabase, String userDatabase,
                             String passwordDatabase) {
}

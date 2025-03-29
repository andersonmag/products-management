package com.github.andersonmag.tenantsmanagejava.tenant;

import jakarta.validation.constraints.NotBlank;

public record TenantRequest(@NotBlank String name,
                            @NotBlank String domain,
                            @NotBlank String urlDatabase,
                            @NotBlank String userDatabase,
                            @NotBlank String passwordDatabase) {
    public Tenant toModel() {
        return new Tenant(this);
    }
}

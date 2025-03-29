package com.github.andersonmag.tenantsmanagejava.tenant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TenantService {

    private final TenantRepository repository;

    public TenantService(TenantRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Tenant create(TenantRequest request) {
        return repository.save(request.toModel());
    }

    @Transactional(readOnly = true)
    public Tenant getById(Long idTenant) {
        return repository.findById(idTenant).orElseThrow();
    }
}

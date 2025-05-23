package com.github.andersonmag.tenantsmanagejava.tenant;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public Tenant getByDomain(String domain) {
        return repository.findByDomain(domain).orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<Tenant> getAll() {
        return repository.findAll();
    }
}

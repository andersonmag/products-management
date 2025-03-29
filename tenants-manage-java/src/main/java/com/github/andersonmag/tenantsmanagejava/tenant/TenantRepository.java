package com.github.andersonmag.tenantsmanagejava.tenant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TenantRepository extends JpaRepository<Tenant, Long> {
}

package com.github.andersonmag.tenantsmanagejava.tenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "tenants")
public class Tenant {
    @Id
    @Column(unique = true, nullable = false, length = 50)
    private String domain;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false)
    private String urlDatabase;
    @Column(nullable = false)
    private String userDatabase;
    @Column(nullable = false)
    private String passwordDatabase;

    public Tenant(TenantRequest request) {
        BeanUtils.copyProperties(request, this);
    }
}

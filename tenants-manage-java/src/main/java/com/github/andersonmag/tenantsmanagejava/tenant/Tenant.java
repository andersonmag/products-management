package com.github.andersonmag.tenantsmanagejava.tenant;

import jakarta.persistence.*;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String domain;
    private String urlDatabase;
    private String userDatabase;
    private String passwordDatabase;

    public Tenant(TenantRequest request) {
        BeanUtils.copyProperties(request, this);
    }
}

package com.github.andersonmag.tenantsmanagejava.tenant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@AllArgsConstructor
@RequestMapping("/tenants")
public class TenantController {

    private final TenantService service;

    @PostMapping
    public ResponseEntity<HttpMethod> createTenant(@RequestBody @Valid TenantRequest request) {
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(service.create(request).getDomain()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{domain}")
    public ResponseEntity<Tenant> getTenant(@PathVariable String domain) {
        return ResponseEntity.ok(service.getByDomain(domain));
    }

    @GetMapping
    public ResponseEntity<List<Tenant>> getTenants() {
        return ResponseEntity.ok(service.getAll());
    }
}

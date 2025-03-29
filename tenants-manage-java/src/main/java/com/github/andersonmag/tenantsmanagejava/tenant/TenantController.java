package com.github.andersonmag.tenantsmanagejava.tenant;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@AllArgsConstructor
@RequestMapping("/tenants")
public class TenantController {

    private final TenantService service;

    @PostMapping
    public ResponseEntity<HttpMethod> createProduct(@RequestBody @Valid TenantRequest request) {
        var created = service.create(request);
        var location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
                .buildAndExpand(created.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Tenant> getTenant(@PathVariable("id") Long idTenant) {
        return ResponseEntity.ok(service.getById(idTenant));
    }
}

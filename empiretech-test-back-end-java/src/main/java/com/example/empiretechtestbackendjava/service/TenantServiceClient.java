package com.example.empiretechtestbackendjava.service;

import com.example.empiretechtestbackendjava.domain.dtos.TenantResponse;
import com.example.empiretechtestbackendjava.exception.TenantGetException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Service
public class TenantServiceClient {

    private final RestTemplate restTemplate;
    @Value(value = "${tenant.service.url}")
    private String tenantServiceUrl;

    public TenantServiceClient() {
        this.restTemplate = new RestTemplate();
    }

    public TenantResponse getTenant(String tenantDomain) {
        try {
            return restTemplate.getForObject(String.format("%s/%s", tenantServiceUrl, tenantDomain), TenantResponse.class);
        } catch (RestClientException e) {
            throw new TenantGetException("Não foi possível buscar o tenant", e);
        }
    }
}

package com.example.empiretechtestbackendjava.config;

import com.example.empiretechtestbackendjava.domain.dtos.TenantResponse;

public class TenantContext {
    private static final ThreadLocal<TenantResponse> CURRENT_TENANT = new ThreadLocal<>();

    private TenantContext() {
    }

    public static void setTenant(TenantResponse tenant) {
        CURRENT_TENANT.set(tenant);
    }

    public static TenantResponse getTenant() {
        return CURRENT_TENANT.get();
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}

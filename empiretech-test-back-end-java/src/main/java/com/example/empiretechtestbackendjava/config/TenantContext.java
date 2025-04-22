package com.example.empiretechtestbackendjava.config;

public class TenantContext {
    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    private TenantContext() {
    }

    public static void setTenant(String tenantDomain) {
        CURRENT_TENANT.set(tenantDomain);
    }

    public static String getTenant() {
        return CURRENT_TENANT.get();
    }

    public static void clear() {
        CURRENT_TENANT.remove();
    }
}

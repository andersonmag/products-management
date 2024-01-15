package com.example.empiretechtestbackendjava.config;

import lombok.AllArgsConstructor;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

@AllArgsConstructor
@EnableCaching
@Configuration
public class CustomCacheConfig implements CachingConfigurer {

    private final DataSourceTenantConfig dataSourceTenantConfig;

    @Override
    public KeyGenerator keyGenerator() {
        return (target, method, params) -> dataSourceTenantConfig.getCurrentTenant();
    }
}

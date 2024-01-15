package com.example.empiretechtestbackendjava.config;

import lombok.AllArgsConstructor;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@AllArgsConstructor
@Configuration
public class CustomKeyCacheConfig implements KeyGenerator {

    private final DataSourceTenantConfig dataSourceTenantConfig;

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return dataSourceTenantConfig.getCurrentTenant();
    }
}

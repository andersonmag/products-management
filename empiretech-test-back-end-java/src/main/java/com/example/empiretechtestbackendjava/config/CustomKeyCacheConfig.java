package com.example.empiretechtestbackendjava.config;

import com.example.empiretechtestbackendjava.domain.dtos.TenantResponse;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class CustomKeyCacheConfig implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        final TenantResponse tenant = TenantContext.getTenant();
        return tenant != null ? tenant.domain() : "";
    }
}

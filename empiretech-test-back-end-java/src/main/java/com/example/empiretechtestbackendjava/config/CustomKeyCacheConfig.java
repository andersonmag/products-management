package com.example.empiretechtestbackendjava.config;

import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

@Configuration
public class CustomKeyCacheConfig implements KeyGenerator {

    @Override
    public Object generate(Object target, Method method, Object... params) {
        return TenantContext.getTenant();
    }
}

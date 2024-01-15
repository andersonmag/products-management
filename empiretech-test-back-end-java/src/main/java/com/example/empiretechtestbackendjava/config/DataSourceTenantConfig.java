package com.example.empiretechtestbackendjava.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.AllArgsConstructor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
@AllArgsConstructor
public class DataSourceTenantConfig {

    private final DataSourcesPropertiesConfig dataSourcesProperties;
    private static final ThreadLocal<String> CURRENT_TENANT = new ThreadLocal<>();

    @Bean(name = "spring.datasource")
    public HikariDataSource defineDataSourceByTenantUser() {
        var currentTenant = CURRENT_TENANT.get();
        DataSourceProperties dataSourceProperties = currentTenant == null ? getDefaultDataSource().getValue()
                : dataSourcesProperties.getDatasources().get(currentTenant);

        return dataSourceProperties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    public String getCurrentTenant() {

        if(CURRENT_TENANT.get() == null) {
            var defaultTenant = getDefaultDataSource().getKey();
            return defaultTenant;
        }
        return CURRENT_TENANT.get();
    }

    public void setCurrentTenant(String tenant) {
        CURRENT_TENANT.set(tenant);
    }

    private Map.Entry<String, DataSourceProperties> getDefaultDataSource() {
        return dataSourcesProperties.getDatasources().entrySet().stream().findFirst()
                .orElseThrow(() -> new RuntimeException("Configure algum database para tenants."));
    }
}

package com.example.empiretechtestbackendjava.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import java.util.LinkedHashMap;
import java.util.Map;

@ConfigurationProperties(prefix = "tenants")
public class DataSourcesPropertiesConfig {

    private final Map<String, DataSourceProperties> datasources = new LinkedHashMap<>();

    public Map<String, DataSourceProperties> getDatasources() {
        return datasources;
    }
}

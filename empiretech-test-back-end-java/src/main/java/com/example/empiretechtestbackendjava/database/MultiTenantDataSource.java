package com.example.empiretechtestbackendjava.database;

import com.example.empiretechtestbackendjava.config.TenantContext;
import com.example.empiretechtestbackendjava.domain.dtos.TenantResponse;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

public class MultiTenantDataSource extends AbstractRoutingDataSource {

    private final Map<Object, Object> dataSources = new HashMap<>();

    public MultiTenantDataSource() {
        setTargetDataSources(dataSources);
        setDefaultTargetDataSource(createDefaultDatabase());
        afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        final TenantResponse tenant = TenantContext.getTenant();
        return tenant != null ? tenant.domain() : null;
    }

    public void addTenant(TenantResponse tenant) {
        if (!dataSources.containsKey(tenant.domain())) {
            DataSource dataSource = createDataSource(tenant.urlDatabase(), tenant.userDatabase(), tenant.passwordDatabase());
            dataSources.put(tenant.domain(), dataSource);
            setTargetDataSources(dataSources);
            afterPropertiesSet();
            this.initSchemaForTenant(dataSource);
        }
    }

    private DataSource createDataSource(String dbUrl, String username, String password) {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dbUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName("org.postgresql.Driver");
        return dataSource;
    }

    private DataSource createDefaultDatabase() {
        return createDataSource("jdbc:postgresql://localhost:5432/" + "tenant1", "postgres", "root");
    }

    public void initSchemaForTenant(final DataSource dataSource) {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource);
        entityManagerFactoryBean.setPackagesToScan("com.example.empiretechtestbackendjava.domain.entities");
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");

        entityManagerFactoryBean.setJpaPropertyMap(properties);
        entityManagerFactoryBean.afterPropertiesSet();
    }
}

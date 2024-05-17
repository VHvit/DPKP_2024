package com.example.config.peruser;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;


public class DynamicRoutingDataSource extends AbstractRoutingDataSource {


    public Map<Object, Object> dataSources = new HashMap<>();
    private final DriverManagerDataSource dataSource;

    DynamicRoutingDataSource(Environment environment) {
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("org.postgresql.Driver");
        dataSource.setUrl(environment.getProperty("spring.datasource.url"));
        dataSource.setUsername(environment.getProperty("spring.datasource.username"));
        dataSource.setPassword(environment.getProperty("spring.datasource.password"));
        dataSources.put("postgres", dataSource);
        super.setTargetDataSources(dataSources);
        super.setDefaultTargetDataSource(dataSource);
        super.afterPropertiesSet();
    }

    @Override
    protected Object determineCurrentLookupKey() {
        // Использовать текущего пользователя из контекста
        return UserContext.getCurrentUser();
    }

    @Override
    protected DataSource determineTargetDataSource() {
        String lookupKey = (String) determineCurrentLookupKey();
        return (DataSource) dataSources.getOrDefault(lookupKey, dataSource);
    }

    public void addDataSource(String key, DataSource dataSource) {
        dataSources.put(key, dataSource);
    }
}

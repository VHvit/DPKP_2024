package com.example.config.peruser;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class DataSourceConfig {

    private final Environment environment;

    @Bean
    public DynamicRoutingDataSource dynamicRoutingDataSource() {
        DynamicRoutingDataSource routingDataSource = new DynamicRoutingDataSource(environment);
        // Настройте маршрутизатор данных (например, установите источники данных по умолчанию)
        return routingDataSource;
    }
}

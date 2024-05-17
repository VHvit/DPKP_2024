package com.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.sql.DataSource;

//@Configuration
//public class DatabaseConfig {
//
//    @Autowired
//    private Environment environment;
//
//    @Bean
//    @RequestScope
//    public DataSource dataSourcePerUser(DatabaseFactory databaseFactory) {
//        return databaseFactory.createDataSource();
//    }
//
//    @Bean
//    @Primary
//    @Scope("singleton")
//    public DataSource dataSourceSingleton(DatabaseFactory databaseFactory) {
//        return databaseFactory.createDataSource();
//    }
//
//    @Bean
//    public DatabaseFactory databaseFactory(DatabaseCredentialsProvider credentialsProvider) {
//        return new DatabaseFactory(environment, credentialsProvider);
//    }
//}
//
//class DatabaseFactory {
//    private final Environment environment;
//    private final DatabaseCredentialsProvider credentialsProvider;
//
//    public DatabaseFactory(Environment environment, DatabaseCredentialsProvider credentialsProvider) {
//        this.environment = environment;
//        this.credentialsProvider = credentialsProvider;
//    }
//
//    public DataSource createDataSource() {
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        if (attributes != null) {
//            DatabaseCredentials credentials = credentialsProvider.getDatabaseCredentialsFromRequest();
//            DriverManagerDataSource dataSource = new DriverManagerDataSource();
//            dataSource.setDriverClassName("org.postgresql.Driver");
//            dataSource.setUrl(environment.getProperty("spring.datasource.url"));
//            dataSource.setUsername(credentials.getUsername());
//            dataSource.setPassword(credentials.getPassword());
//            return dataSource;
//        } else {
//            DriverManagerDataSource dataSource = new DriverManagerDataSource();
//            dataSource.setDriverClassName("org.postgresql.Driver");
//            dataSource.setUrl(environment.getProperty("spring.datasource.url"));
//            dataSource.setUsername(environment.getProperty("spring.datasource.username"));
//            dataSource.setPassword(environment.getProperty("spring.datasource.password"));
//            return dataSource;
//        }
//    }
//}
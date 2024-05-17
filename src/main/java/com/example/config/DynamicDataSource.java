package com.example.config;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.web.context.request.RequestContextHolder;

public class DynamicDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        // Здесь мы можем определить, какой источник данных использовать
        // Например, использовать значение из контекста запроса
        return RequestContextHolder.getRequestAttributes() != null ? "perUser" : "singleton";
    }
}

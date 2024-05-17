package com.example.config.peruser;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;

import javax.sql.DataSource;
import java.io.IOException;

@RequiredArgsConstructor
@Component
public class BasicAuthFilter implements Filter {

    private final DynamicRoutingDataSource routingDataSource;
    private final DataSourceFactory dataSourceFactory;
    private final Environment environment;

    @Override
    @SneakyThrows
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        // Извлечь учетные данные из заголовка
        String[] credentials = BasicAuthUtils.parseBasicAuth(httpRequest);

        if (credentials != null && credentials.length == 2) {
            String username = credentials[0];
            String password = credentials[1];

            if(username.equals(environment.getProperty("spring.datasource.username"))) {
                throw new IllegalAccessException();
            }
            String dbUrl = environment.getProperty("spring.datasource.url");
            DataSource newDataSource = dataSourceFactory.createDataSource(username, password, dbUrl);
            routingDataSource.addDataSource(username, newDataSource);
            UserContext.setCurrentUser(username);
        }

        try {
            // Продолжить обработку запроса
            chain.doFilter(request, response);
        } finally {
            // Убедиться, что контекст очищен
            UserContext.clear();
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    @Override
    public void destroy() {}
}

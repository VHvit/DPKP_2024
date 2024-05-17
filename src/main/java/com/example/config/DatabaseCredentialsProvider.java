package com.example.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class DatabaseCredentialsProvider {

    @Autowired
    private HttpServletRequest request;

    public DatabaseCredentials getDatabaseCredentialsFromRequest() {
        HttpHeaders headers = new ServletServerHttpRequest(request).getHeaders();
        String authHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        String[] credentials = new String(Base64.getDecoder().decode(authHeader.split(" ")[1])).split(":");
        DatabaseCredentials databaseCredentials = new DatabaseCredentials();
        databaseCredentials.setUsername(credentials[0]);
        databaseCredentials.setPassword(credentials[1]);
        databaseCredentials.setUrl("jdbc:postgresql://your-database-url");
        return databaseCredentials;
    }
}
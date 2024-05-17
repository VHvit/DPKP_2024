package com.example.config.peruser;

import java.nio.charset.StandardCharsets;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.Base64Utils;

public class BasicAuthUtils {

    public static String[] parseBasicAuth(HttpServletRequest request) {
        String authorization = request.getHeader("Authorization");
        if (authorization != null && authorization.startsWith("Basic ")) {
            String base64Credentials = authorization.substring("Basic ".length()).trim();
            String decodedCredentials = new String(Base64Utils.decodeFromString(base64Credentials), StandardCharsets.UTF_8);
            return decodedCredentials.split(":", 2); // Ожидается формат "username:password"
        }
        return new String[]{null, null};
    }
}

package com.baeldung.basicauth;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class BasicAuthExtractor {

    public static String[] extractCredentials(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Basic ")) {
            String base64Credentials = authHeader.substring("Basic ".length()).trim();
            byte[] credDecoded = Base64.getDecoder().decode(base64Credentials);
            String credentials = new String(credDecoded, StandardCharsets.UTF_8);
            final String[] values = credentials.split(":", 2);

            if (values.length == 2) {
                return values;
            }
        }
        return null;
    }
}
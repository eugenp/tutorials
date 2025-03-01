package com.baeldung.cookiemanagement.config;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

public class CsvUserDetailsUtils {

    private static final int FIELDS = 3;

    private CsvUserDetailsUtils() {
    }

    public static List<UserDetails> read(ClassPathResource resource) throws IOException {
        List<UserDetails> userDetailsList = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            String line;
            boolean firstLine = true;
            while ((line = reader.readLine()) != null) {
                if (firstLine) {
                    firstLine = false;
                    continue;
                }

                String[] tokens = line.split(",", FIELDS);
                if (tokens.length != FIELDS) {
                    throw new IllegalArgumentException("required fields: " + FIELDS);
                }

                String username = tokens[0].trim();
                String password = tokens[1].trim();
                String rolesStr = tokens[2].trim();

                String[] roles = rolesStr.split("\\|");
                UserDetails user = User.withUsername(username)
                    .password("{noop}" + password)
                    .roles(roles)
                    .build();
                userDetailsList.add(user);
            }
        }
        return userDetailsList;
    }
}
package com.baeldung.auth.server.customclaims.components;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Service
public class UserInfoService {

    static final Map<String, UserInfo> users = Map.of(
        "user", new UserInfo(
        "user",
        "Alice Smith",
        "Alice",
        "Smith",
        "alice.smith@example.com",
        Locale.forLanguageTag("en-US"),
        "female",
        LocalDate.of(1990, 1, 1),
        ZoneId.of("America/New_York"),
        UUID.randomUUID(),
        Instant.now(),
        Instant.now(),
        Instant.now().plus(60, java.time.temporal.ChronoUnit.DAYS)
        )
    );

    public UserInfo getUserInfoByUsername(String username) {
        return users.get(username);
    }

    public record UserInfo(
        String username,
        String name,
        String givenName,
        String familyName,
        String email,
        Locale locale,
        String gender,
        LocalDate birthdate,
        ZoneId zoneId,
        UUID accountId,
        Instant createdAt,
        Instant updatedAt,
        Instant accountExpiresAt
    ) {}
}

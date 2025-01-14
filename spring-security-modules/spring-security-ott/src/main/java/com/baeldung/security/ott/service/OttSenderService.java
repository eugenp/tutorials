package com.baeldung.security.ott.service;

import java.time.Instant;
import java.util.Optional;

public interface OttSenderService {

    void sendTokenToUser(String username, String token, Instant expirationTime);
    Optional<String> getLastTokenForUser(String username);
}

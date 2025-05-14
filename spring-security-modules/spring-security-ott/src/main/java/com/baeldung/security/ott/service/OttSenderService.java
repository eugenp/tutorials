package com.baeldung.security.ott.service;

import java.time.Instant;
import java.util.Optional;

public interface OttSenderService {

    void sendTokenToUser(String username, String token, Instant expirationTime);

    // Optional method used for tests
    default Optional<String> getLastTokenForUser(String username) { return Optional.empty();}
}

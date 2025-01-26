package com.baeldung.security.ott.service;

import lombok.extern.slf4j.Slf4j;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Slf4j
public class FakeOttSenderService implements OttSenderService {

    private final Map<String,String> lastTokenByUser = new HashMap<>();

    @Override
    public void sendTokenToUser(String username, String token, Instant expiresAt) {
        lastTokenByUser.put(username, token);
        log.info("Sending token to username '{}'. token={}, expiresAt={}", username,token,expiresAt);
    }

    @Override
    public Optional<String> getLastTokenForUser(String username) {
        return Optional.ofNullable(lastTokenByUser.get(username));
    }
}

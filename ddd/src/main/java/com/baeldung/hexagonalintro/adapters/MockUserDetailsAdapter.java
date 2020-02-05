package com.baeldung.hexagonalintro.adapters;

import com.baeldung.hexagonalintro.ports.UserDetailsPort;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Repository
class MockUserDetailsAdapter implements UserDetailsPort {

    private final ConcurrentHashMap<String, String> usernamesAndPasswords = new ConcurrentHashMap<>();

    @Override
    public boolean isCorrectPassword(String username, String newPassword) {
        return Objects.equals(usernamesAndPasswords.get(username), newPassword);
    }

    @Override
    public void updatePassword(String username, String newPassword) {
        usernamesAndPasswords.put(username, newPassword);
    }
}

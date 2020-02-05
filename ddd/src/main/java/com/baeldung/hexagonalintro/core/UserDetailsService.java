package com.baeldung.hexagonalintro.core;

import com.baeldung.hexagonalintro.ports.UserDetailsPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import static lombok.AccessLevel.PACKAGE;

@Service
@RequiredArgsConstructor(access = PACKAGE)
public class UserDetailsService {

    private final UserDetailsPort userDetailsPort;

    public void updatePassword(String username, String oldPassword, String newPassword) {
        if (userDetailsPort.isCorrectPassword(username, oldPassword)) {
            userDetailsPort.updatePassword(username, newPassword);
        }
    }
}

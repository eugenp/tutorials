package com.baeldung.hexagonalarchitecture.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class User {

    private String username;
    private UserStatus status;
    private LocalDateTime activeSince;
    private LocalDateTime activeUntil;

    public User(String username) {
        this.username = username;
        this.status = UserStatus.CREATED;
    }

    public void activateUser() {
        this.status = UserStatus.ACTIVE;
        this.activeSince = LocalDateTime.now();
        this.activeUntil = null;
    }

    public void deactivateUser() {
        this.status = UserStatus.INACTIVE;
        this.activeUntil = LocalDateTime.now();
    }
}

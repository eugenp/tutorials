package com.baeldung.hexagonalarchitecture.domain.model;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
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

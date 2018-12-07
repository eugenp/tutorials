package org.baeldung.hexagonal.domain.core;

import java.time.LocalDateTime;

public class User {

    private LocalDateTime dateTimeCreated;
    private String username;
    private String password;

    public User(LocalDateTime dateTimeCreated, String username, String password) {
        this.dateTimeCreated = dateTimeCreated;
        this.username = username;
        this.password = password;
    }

    public LocalDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}

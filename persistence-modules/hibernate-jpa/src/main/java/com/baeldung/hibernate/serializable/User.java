package com.baeldung.hibernate.serializable;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

@Entity
public class User {

    @EmbeddedId private UserId userId;
    private Email email;


    public User() {
    }

    public User(UserId userId, Email email) {
        this.userId = userId;
        this.email = email;
    }

    public UserId getUserId() {
        return userId;
    }

    public void setUserId(UserId userId) {
        this.userId = userId;
    }

    public Email getEmail() {
        return email;
    }

    public void setEmail(Email email) {
        this.email = email;
    }
}

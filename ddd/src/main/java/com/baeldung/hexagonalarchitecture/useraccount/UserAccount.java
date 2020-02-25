package com.baeldung.hexagonalarchitecture.useraccount;

import java.util.UUID;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 05:39
 */
public class UserAccount {
    private Long id;
    private String username;
    private String password;
    private UserAccountEmail email;
    private String verificationToken;
    private boolean active;
    private boolean blocked;

    private UserAccount(String username, String password, UserAccountEmail email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.verificationToken = UUID.randomUUID().toString();
    }

    private UserAccount(Long id, String username, String password, UserAccountEmail email, String verificationToken,
                        Boolean active, boolean blocked) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.email = email;
        this.verificationToken = verificationToken;
    }

    public static UserAccount of(String username, String password, UserAccountEmail email) {
        return new UserAccount(username, password, email);
    }

    public static UserAccount of(Long id, String username, String password, UserAccountEmail email,
                                 String verificationToken, Boolean active, boolean blocked) {
        return new UserAccount(id, username, password, email, verificationToken, active, blocked);
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public UserAccountEmail getEmail() {
        return email;
    }

    public String getVerificationToken() {
        return verificationToken;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    @Override
    public String toString() {
        return "UserAccount{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email=" + email +
                ", verificationToken='" + verificationToken + '\'' +
                ", active=" + active +
                ", blocked=" + blocked +
                '}';
    }
}

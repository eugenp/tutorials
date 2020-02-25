package com.baeldung.hexagonalarchitecture.useraccount;

/**
 * @author Fact S Musingarimi
 * 23/2/2020
 * 13:57
 */
public class UserAccountEmail {
    private String email;
    private boolean confirmed;

    private UserAccountEmail(String email) {
        this.email = email;
    }

    private UserAccountEmail(String email, boolean confirmed) {
        this.email = email;
        this.confirmed = confirmed;
    }

    public static UserAccountEmail of(String email) {
        return new UserAccountEmail(email);
    }

    public static UserAccountEmail of(String email, boolean confirmed) {
        return new UserAccountEmail(email, confirmed);
    }

    public String getEmail() {
        return email;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    @Override
    public String toString() {
        return "UserAccountEmail{" +
                "email='" + email + '\'' +
                ", confirmed=" + confirmed +
                '}';
    }
}

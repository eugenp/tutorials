package com.baeldung.groupedvsmultipleassertions;

public class User {
    private String username;
    private String email;
    private boolean activated;

    public User(String username, String email, boolean activated) {
        this.username = username;
        this.email = email;
        this.activated = activated;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }
}

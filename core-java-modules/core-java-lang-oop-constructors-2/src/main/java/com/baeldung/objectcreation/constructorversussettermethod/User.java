package com.baeldung.objectcreation.constructorversussettermethod;

public class User {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        if (username.matches("[a-zA-Z0-9_]+")) {
            this.username = username;
        } else {
            throw new IllegalArgumentException("Invalid username format");
        }
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if (password.length() >= 8) {
            this.password = password;
        } else {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
    }
}


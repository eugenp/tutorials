package com.baeldung.user;

public class CmsUser {
    private String username;
    private String role;

    public CmsUser(String username, String role) {
        this.username = username;
        this.role = role;
    }

    public String getRole() {
        return this.role;
    }
}

package com.baeldung.springsecurity.dto;

import com.baeldung.springsecurity.utils.Role;

public class UserProfileDto {
    private String username;
    private String email;
    private Role role;

    public UserProfileDto() {
    }

    public UserProfileDto(String username, String email, Role role) {
        this.username = username;
        this.email = email;
        this.role = role;
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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

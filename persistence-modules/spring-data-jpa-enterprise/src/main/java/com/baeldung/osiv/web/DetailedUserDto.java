package com.baeldung.osiv.web;

import com.baeldung.osiv.model.BasicUser;

import java.util.Set;

public class DetailedUserDto {

    private Long id;
    private String username;
    private Set<String> permissions;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<String> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<String> permissions) {
        this.permissions = permissions;
    }

    public static DetailedUserDto fromEntity(BasicUser user) {
        DetailedUserDto detailed = new DetailedUserDto();
        detailed.setId(user.getId());
        detailed.setUsername(user.getUsername());
        detailed.setPermissions(user.getPermissions());

        return detailed;
    }
}

package com.baeldung.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "users")
public class BasicUser {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    @ElementCollection
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
}

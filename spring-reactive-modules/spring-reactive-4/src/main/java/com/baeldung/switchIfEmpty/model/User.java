package com.baeldung.switchIfEmpty.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    @JsonProperty("id")
    private String id;

    @JsonProperty("email")
    private String email;

    @JsonProperty("username")
    private String username;

    @JsonProperty("roles")
    private String roles;

    public User(){}

    public User(String id, String email, String username, String roles) {
        this.id = id;
        this.email = email;
        this.username = username;
        this.roles = roles;
    }

    public String getId() {
        return id;
    }
}
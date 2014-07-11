package org.baeldung.jackson.field;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class User {

    private int id;
    private String name;
    @JsonIgnore
    private String password;

    public User() {
        super();
    }

    public User(final int id, final String name, final String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    // API
    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @JsonProperty
    public void setPassword(final String password) {
        this.password = password;
    }

}

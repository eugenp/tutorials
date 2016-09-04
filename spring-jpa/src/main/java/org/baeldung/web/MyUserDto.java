package org.baeldung.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MyUserDto {
    @NotNull
    @Size(min = 1)
    private String username;

    @NotNull
    @Size(min = 1)
    private String name;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

}

package org.baeldung.web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class MyUserDto {
    @NotNull
    @Size(min = 1)
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

}

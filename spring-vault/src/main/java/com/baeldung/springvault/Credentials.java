package com.baeldung.springvault;

import org.springframework.data.annotation.Id;
import org.springframework.vault.repository.mapping.Secret;

@Secret(backend = "credentials", value = "myapp")
public class Credentials {

    @Id
    private String username;
    private String password;

    public Credentials() {

    }

    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "Credential [username=" + username + ", password=" + password + "]";
    }

}

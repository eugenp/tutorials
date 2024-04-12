package com.baeldung.jackson.jsonignorevstransient;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;

@Entity
@Table(name = "Users")
class User implements Serializable {

    @Id
    private Long id;

    private String username;

    private String password;

    @Transient
    private String repeatedPassword;

    public User() {
    }

    public User(Long id, String username, String password, String repeatedPassword) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.repeatedPassword = repeatedPassword;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepeatedPassword() {
        return repeatedPassword;
    }

    public void setRepeatedPassword(String repeatedPassword) {
        this.repeatedPassword = repeatedPassword;
    }
}

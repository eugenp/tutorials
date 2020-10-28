package com.baeldung.ignorable.fields;

import java.io.Serializable;
import java.util.StringJoiner;

import javax.persistence.*;

@Entity
@Table(name = "Users")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    private String password;
    @Transient
    private String currentDevice;

    // Needed for Hibernate mapping
    public User() {
    }

    public User(String email, String password, String currentDevice) {
        this.email = email;
        this.password = password;
        this.currentDevice = currentDevice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCurrentDevice() {
        return currentDevice;
    }

    public void setCurrentDevice(String currentDevice) {
        this.currentDevice = currentDevice;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "[", "]").add("id=" + id)
            .add("email='" + email + "'")
            .add("password='" + password + "'")
            .add("currentDevice='" + currentDevice + "'")
            .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof User))
            return false;
        User user = (User) o;
        return email.equals(user.email);
    }
}
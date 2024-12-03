package com.baeldung.objecthydration;

import java.io.Serializable;
import java.util.Objects;

public class User implements Serializable {
    private String uId;
    private String firstName;
    private String lastName;
    private String alias;

    public User() {
    }

    public User(String firstName, String uId, String lastName, String alias) {
        this.firstName = firstName;
        this.uId = uId;
        this.lastName = lastName;
        this.alias = alias;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        User user = (User) o;
        return Objects.equals(firstName, user.firstName) && Objects.equals(uId, user.uId) && Objects.equals(lastName, user.lastName) && Objects.equals(alias, user.alias);
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void generateMyUser() {
        this.setAlias("007");
        this.setFirstName("James");
        this.setLastName("Bond");
        this.setuId("JB");
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, uId, lastName, alias);
    }
}

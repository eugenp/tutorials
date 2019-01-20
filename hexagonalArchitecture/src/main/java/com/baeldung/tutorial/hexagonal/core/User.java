package com.baeldung.tutorial.hexagonal.core;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * A domian object which contains the necessary data of the user which will be used by core.
 */
public final class User {
    @Setter
    @Getter
    public String name;

    @Setter @Getter
    public String email;

    @Setter @Getter
    public String address;

    @Setter @Getter
    public Long uniqueId;

    @Setter @Getter
    public UserType userType;

    @Setter @Getter
    public Date userRegisteredDate;

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", uniqueId=" + uniqueId +
                ", userType=" + userType +
                ", userRegisteredDate=" + userRegisteredDate +
                '}';
    }
}

package com.baeldung.truth;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class User implements Comparable<User> {
    private String name = "John Doe";
    private List<String> emails = Arrays.asList("contact@baeldung.com", "staff@baeldung.com");

    public User() {
    }

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getEmails() {
        return emails;
    }

    public void setEmails(List<String> emails) {
        this.emails = emails;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        final User other = (User) obj;
        return Objects.equals(this.name, other.name);
    }

    @Override
    public int compareTo(User o) {
        return this.getName()
            .compareToIgnoreCase(o.getName());
    }

}

package org.baeldung.domain;

public class UserVO {

    private String name;

    public UserVO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("User [%s,]", name);
    }
}

package com.baeldung.bean.injection.constructor.xml;

import org.springframework.stereotype.Component;

@Component(value = "user")
public class User {

    private Organization organization;

    public User(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "User [organization=" + organization + "]";
    }

}

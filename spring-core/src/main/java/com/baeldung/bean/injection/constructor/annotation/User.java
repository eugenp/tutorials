package com.baeldung.bean.injection.constructor.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "user")
public class User {

    private Organization organization;

    @Autowired
    public User(Organization organization) {
        this.organization = organization;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "User [organization=" + organization + "]";
    }

}

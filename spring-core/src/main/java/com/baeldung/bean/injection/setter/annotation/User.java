package com.baeldung.bean.injection.setter.annotation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component(value = "user")
public class User {

    private Organization organization;

    public Organization getOrganization() {
        return organization;
    }

    @Autowired
    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public String toString() {
        return "User [organization=" + organization + "]";
    }

}

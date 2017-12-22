package com.baeldung.bean.injection.setter.xml;

public class User {

    private Organization organization;

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

package org.baeldung.dto;

public class User {
    private String fullName;
    private String lastName;
    private String description;
    private String organization;
    private String company;
    private String mobile;

    public String getDescription() {
        return description;
    }
    public User setDescription(String description) {
        this.description = description;
        return this;
    }
    public String getFullName() {
        return fullName;
    }
    public User setFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }
    public String getLastName() {
        return lastName;
    }
    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
    public String getCompany() {
        return company;
    }
    public User setCompany(String company) {
        this.company = company;
        return this;
    }
    public String getMobile() {
        return mobile;
    }
    public User setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }

    public String getOrganization() {
        return organization;
    }

    public User setOrganization(String organization) {
        this.organization = organization;
        return this;
    }
}

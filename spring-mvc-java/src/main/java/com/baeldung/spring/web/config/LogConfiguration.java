package com.baeldung.spring.web.config;

public class LogConfiguration {
    private String companyName = "MyCompany";
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(final String companyName) {
        this.companyName = companyName;
    }
}

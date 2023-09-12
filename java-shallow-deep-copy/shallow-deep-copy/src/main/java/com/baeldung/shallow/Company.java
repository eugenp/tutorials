package com.baeldung.shallow;

public class Company{
    private String companyName;
    private String industry;
    private int numberOfEmployees;
    public Company(String companyName, String industry, int numberOfEmployees){
        this.companyName = companyName;
        this.industry = industry;
        this.numberOfEmployees = numberOfEmployees;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public int getNumberOfEmployees() {
        return numberOfEmployees;
    }

    public void setNumberOfEmployees(int numberOfEmployees) {
        this.numberOfEmployees = numberOfEmployees;
    }

}

package com.baeldung.hexagonal.banking.domain;

public class Company extends AccountHolder {
    
    private String companyName;

    public Company(String companyName, int taxId) {
        super(taxId, "Commercial");
        this.companyName = companyName;
    }

    public String getCompanyName() {

        return companyName;
    }
}

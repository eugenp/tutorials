package com.baeldung.dipractice;

import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Unit {

    private Company company;

    @Autowired
    @Named("company")
    public Unit(Company company) {
        this.company = company;
    }

    public String getDetails() {
        System.out.println("Unit's Company owner:" + company.getOwner());
        System.out.println("Unit's Company name:" + company.getName());
        return company.getOwner();
    }
}

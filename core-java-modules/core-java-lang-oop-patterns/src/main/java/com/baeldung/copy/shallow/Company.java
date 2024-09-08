package com.baeldung.copy.shallow;

import java.util.Objects;

public class Company implements Cloneable {

    private String companyName;
    private Founder founder;

    public Company() {
    }

    public Company(String companyName, Founder founder) {
        this.companyName = companyName;
        this.founder = founder;
    }

    public Company(Company original) {
        this.companyName = original.companyName;
        this.founder = original.founder;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Founder getFounder() {
        return founder;
    }

    public void setFounder(Founder founder) {
        this.founder = founder;
    }

    @Override
    public Company clone() {
        try {
            return (Company) super.clone();
        } catch (CloneNotSupportedException e) {
            // shouldn't happen since we're Cloneable
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Company that = (Company) o;
        return Objects.equals(companyName, that.companyName) && Objects.equals(founder, that.founder);
    }

    @Override
    public int hashCode() {
        return Objects.hash(companyName, founder);
    }
}

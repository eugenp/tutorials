package com.baeldung.deepvsshallowcopy;

public class Employee implements Cloneable {

    private String name;
    private Company company;

    public Employee(String name, Company company) {
        this.name = name;
        this.company = company;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public Employee clone() {
        try {
            Employee employee = (Employee) super.clone();
            employee.company = company.clone();
            return employee;
        } catch (CloneNotSupportedException e) {
            return new Employee(name, company.clone());
        }
    }
}

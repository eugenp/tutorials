package com.baeldung.dipractice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Employee {

    private Person person;
    private Company company;
    private Salary salary;

    @Autowired
    public Employee(Person person, Company company, Salary salary) {
        this.person = person;
        this.company = company;
        this.salary = salary;
    }

    public void getDetails() {
        System.out.println("Person age:" + person.getAge());
        System.out.println("Person name:" + person.getName());
        System.out.println("Company owner:" + company.getOwner());
        System.out.println("Company name:" + company.getName());
        System.out.println("Salary Details for experience:" + salary.getExperience());
        System.out.println("Salary Details:" + salary.getAmount());
    }
}

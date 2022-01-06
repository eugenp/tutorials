package com.baeldung.implicitsuperconstructorundefined;

public class Employee extends Person {

    private Double salary;

    public Employee(String name, Integer age, Double salary) {
        // comment this super call to see the error.
        super(name, age);
        this.salary = salary;
    }

    public Employee(Double salary) {
        super();
        this.salary = salary;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

}
package com.stackify.stream;

public class Employee {
    private Integer id;
    private String name;
    private Double salary;
    
    public Employee(Integer id, String name, Double salary) {
        this.id = id;
        this.name = name;        
        this.salary = salary;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }
    
    public void salaryIncrement(Double percentage) {
        Double newSalary = salary + percentage * salary / 100;
        setSalary(newSalary);
    }

    public String toString() {
        return "Id: " + id + " Name:" + name + " Price:" + salary;
    }
}

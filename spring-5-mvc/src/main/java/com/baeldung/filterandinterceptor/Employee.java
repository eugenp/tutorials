package com.baeldung.filterandinterceptor;

public class Employee {

    private String id;
    private String name;
    private String role;
    private double price;

    public Employee(String id, String name, String role, double price) {
        this.id = id;
        this.name = name;
        this.role = role;
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public double getPrice() {
        return price;
    }

}

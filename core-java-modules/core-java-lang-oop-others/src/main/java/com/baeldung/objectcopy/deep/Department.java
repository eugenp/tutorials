package com.baeldung.objectcopy.deep;

public class Department implements Cloneable {
    private String name;
    private String street;
    private String city;

    public Department(String name, String street, String city) {
        this.name = name;
        this.street = street;
        this.city = city;
    }

    public Department(Department department) {
        this(department.getName(), department.getStreet(), department.getCity());
    }

    @Override
    public Department clone() throws CloneNotSupportedException {
        return (Department) super.clone();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}

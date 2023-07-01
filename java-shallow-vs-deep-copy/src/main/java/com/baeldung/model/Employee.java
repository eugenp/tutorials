package com.baeldung.model;

public class Employee implements Cloneable {
    private int id;
    private String name;
    private Address address;

    // we can use @Getter and @Setter instead
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Employee deepCopy() {
        Employee employeeCopy = new Employee();
        employeeCopy.id = this.id;
        employeeCopy.name = this.name;
        employeeCopy.address = this.address.deepCopy();
        return employeeCopy;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

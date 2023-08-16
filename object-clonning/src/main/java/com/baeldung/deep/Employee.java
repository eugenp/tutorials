package com.baeldung.deep;

public class Employee implements Cloneable {
    private String name;
    private String age;
    private Address address;

    public Employee(String name, String age, Address address) {
        this.name = name;
        this.age = age;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Employee emp = (Employee) super.clone();
        emp.address = (Address) address.clone();
        return emp;
    }

    @Override
    public String toString() {
        return "[Name: " + name + ", Age: " + age + ", Address:" + address + "]";
    }
}

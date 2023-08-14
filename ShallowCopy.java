package com.demo.shallow;

class Address{
    private String addressLine1;
    private String addressLine2;

    public Address(String addressLine1, String addressLine2) {
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    @Override
    public String toString() {
        return "[AddressLine1: " + addressLine1 + ", AddressLine2: " + addressLine2 + "]";
    }
}
class Emp implements Cloneable{
    private String name;
    private String age;
    private Address address;

    public Emp(String name, String age, Address address) {
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
        return super.clone();
    }

    @Override
    public String toString() {
        return "[Name: " + name + ", Age: " + age + ", Address:" + address + "]";
    }
}
public class ShallowCopy {
    public static void main(String[] args) throws CloneNotSupportedException {
        Address myAddress = new Address("line1", "line2");
        Emp emp1 = new Emp("Sam", "20", myAddress);
        Emp emp2 = (Emp) emp1.clone();

        System.out.println("BEFORE MAKING CHANGES");
        System.out.println(emp1);
        System.out.println(emp2);

        System.out.println("AFTER MAKING CHANGES");
        emp1.getAddress().setAddressLine2("LINE2");
        System.out.println(emp1);
        System.out.println(emp2);
    }
}

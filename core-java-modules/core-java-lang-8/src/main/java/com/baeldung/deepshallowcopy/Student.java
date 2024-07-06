package com.baeldung.deepshallowcopy;

public class Student implements Cloneable{
    private String name;
    private String batch;
    private String department;
    private Address address;

    public Student(String name, String batch, String department, Address address) {
        this.name = name;
        this.batch = batch;
        this.department = department;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Student studentShallowCopy(){
        return new Student(this.name,this.batch,this.department,this.address);
    }

    public Student studentDeepCopy(){
        Address newAddress = new Address(this.address.getStreet(), this.address.getCity(),this.address.getState());
        return new Student(this.name,this.batch,this.department,newAddress);
    }

    @Override
    public Student clone() {
        try {
            Student clone = (Student) super.clone();
            clone.setAddress((Address) this.address.clone());
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", batch='" + batch + '\'' +
                ", department='" + department + '\'' +
                ", address=" + address +
                '}';
    }



}

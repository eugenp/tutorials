package com.baeldung.shallowdeepcopy;

public class Student implements Cloneable {

    String firstName;
    String lastName;
    Teacher classMaster;
    Address address;

    Student(String firstName, String lastName, Teacher classTeacher, Address address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.classMaster = classTeacher;
        this.address = address;
    }

    Student(Student original) {
        this(original.getFirstName(), original.getLastName(), new Teacher(original.getClassMaster()), new Address(original.getAddress()));
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setClassMaster(Teacher classMaster) {
        this.classMaster = classMaster;
    }

    public Teacher getClassMaster() {
        return classMaster;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Student clone = (Student) super.clone();

        clone.setAddress((Address) clone.getAddress()
            .clone());
        clone.setClassMaster((Teacher) clone.getClassMaster()
            .clone());

        return clone;
    }
}
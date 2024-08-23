package com.baeldung.deepcopyvsshallowcopy;

import java.io.Serializable;
import java.util.Objects;

public class Student implements Serializable, Cloneable {

    private int studentId;
    private int creditsEnrolled;
    private Address address;

    public Student(int studentId, int creditsEnrolled, Address address) {
        this.studentId = studentId;
        this.creditsEnrolled = creditsEnrolled;
        this.address = address;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getCreditsEnrolled() {
        return creditsEnrolled;
    }

    public void setCreditsEnrolled(int creditsEnrolled) {
        this.creditsEnrolled = creditsEnrolled;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    //Default version of clone() method.
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Student student = (Student) o;
        return studentId == student.studentId && creditsEnrolled == student.creditsEnrolled && address == student.address;
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, creditsEnrolled, address);
    }

}

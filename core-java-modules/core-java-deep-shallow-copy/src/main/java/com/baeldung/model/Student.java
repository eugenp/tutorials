package com.test.model;

import java.io.Serializable;

public class Student implements Serializable, Cloneable {

    private static final long serialVersionUID = 4559954063813376388L;
    private String firstName;
    private String lastName;
    private College college;

    public Student(String firstName, String lastName, College college) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.college = college;
    }

    public Student(Student that) {
        this(that.getFirstName(), that.getLastName(), new College(that.getCollege()));
    }

    public Student() {
    }

    //Modified clone() method in Student class
    @Override
    public Object clone() {
        Student student;
        try {
            student = (Student) super.clone();
        } catch (CloneNotSupportedException e) {
            student = new Student(this.getFirstName(), this.getLastName(), this.getCollege());
        }
        student.college = (College) this.college.clone();
        return student;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public College getCollege() {
        return college;
    }

    public void setCollege(College college) {
        this.college = college;
    }
}
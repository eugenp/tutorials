package com.baeldung.deepvsshallowcopy;

public class Professor {
    private String firstName;
    private String lastName;
    private Course course;

    public Professor(String firstName, String lastName, Course course) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.course = course;

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

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Professor(Professor professor) {
        this(professor.getFirstName(), professor.getLastName(), new Course(professor.getCourse()));
    }
}

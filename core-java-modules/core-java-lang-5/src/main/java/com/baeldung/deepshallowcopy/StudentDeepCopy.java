package com.baeldung.deepshallowcopy;

public class StudentDeepCopy implements Cloneable{
    public String firstName;
    public String lastName;
    public String level;
    public SchoolDeepCopy school;


    public StudentDeepCopy(String firstName, String lastName, String level, SchoolDeepCopy school){
        this.firstName = firstName;
        this.lastName = lastName;
        this.level = level;
        this.school = school;
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

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public SchoolDeepCopy getSchool() {
        return school;
    }

    public void setSchool(SchoolDeepCopy school) {
        this.school = school;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        StudentDeepCopy student = (StudentDeepCopy) super.clone();
        student.school = (SchoolDeepCopy) school.clone();
        return student;
    }

}

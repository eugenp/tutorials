package com.baeldung.deepshallowcopy;

public class StudentShallowCopy implements Cloneable{
    public String firstName;
    public String lastName;
    public String level;
    public SchoolShallowCopy school;


    public StudentShallowCopy(String firstName, String lastName, String level, SchoolShallowCopy school){
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

    public SchoolShallowCopy getSchool() {
        return school;
    }

    public void setSchool(SchoolShallowCopy school) {
        this.school = school;
    }
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

}

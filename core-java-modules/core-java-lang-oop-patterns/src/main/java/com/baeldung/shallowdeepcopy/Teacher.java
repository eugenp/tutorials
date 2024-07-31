package com.baeldung.shallowdeepcopy;

public class Teacher implements Cloneable {

    String firstName;
    String lastName;

    Teacher(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    Teacher(Teacher original) {
        this(original.getFirstName(), original.getLastName());
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

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}


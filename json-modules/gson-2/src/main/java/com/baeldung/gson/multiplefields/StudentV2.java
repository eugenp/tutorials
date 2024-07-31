package com.baeldung.gson.multiplefields;

import java.util.Objects;

import com.google.gson.annotations.Expose;

public class StudentV2 extends StudentV1 {

    @Expose
    private String firstName;

    @Expose
    private String lastName;

    @Expose
    private String major;

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    // Default constructor for Gson
    public StudentV2() {

    }

    public StudentV2(String firstName, String lastName, String major) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.major = major;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof StudentV2))
            return false;
        StudentV2 studentV2 = (StudentV2) o;
        return Objects.equals(firstName, studentV2.firstName) && Objects.equals(lastName, studentV2.lastName) && Objects.equals(major, studentV2.major);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, major);
    }
}

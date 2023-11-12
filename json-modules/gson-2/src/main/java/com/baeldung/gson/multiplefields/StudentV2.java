package com.baeldung.gson.multiplefields;

import java.util.Objects;

public class StudentV2 extends StudentV1 {

    private String firstName;
    private String lastName;
    private String studentNumber;

    // Default constructor for Gson
    public StudentV2() {

    }

    public StudentV2(String firstName, String lastName, String studentNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentNumber = studentNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof StudentV2))
            return false;
        StudentV2 studentV2 = (StudentV2) o;
        return Objects.equals(firstName, studentV2.firstName) && Objects.equals(lastName, studentV2.lastName) && Objects.equals(studentNumber, studentV2.studentNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, studentNumber);
    }
}

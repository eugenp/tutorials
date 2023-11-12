package com.baeldung.gson.multiplefields;

import java.util.Objects;

import com.google.gson.annotations.Expose;

public class ImprovedStudent extends StudentV2 {

    @Expose
    private StudentIdentification id;

    // Default constructor for Gson
    public ImprovedStudent() {

    }

    public ImprovedStudent(StudentIdentification id) {
        this.id = id;
    }

    public StudentIdentification getId() {
        return id;
    }

    public void setId(StudentIdentification id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "ImprovedStudent{" + "id=" + id + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof ImprovedStudent))
            return false;
        if (!super.equals(o))
            return false;
        ImprovedStudent that = (ImprovedStudent) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), id);
    }

    public static class StudentIdentification {
        @Expose
        private long studentNumber;

        @Expose
        private String firstName;

        @Expose
        private String lastName;

        public StudentIdentification(long studentNumber, String firstName, String lastName) {
            this.studentNumber = studentNumber;
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o)
                return true;
            if (!(o instanceof StudentIdentification))
                return false;
            StudentIdentification that = (StudentIdentification) o;
            return studentNumber == that.studentNumber && Objects.equals(firstName, that.firstName) && Objects.equals(lastName, that.lastName);
        }

        @Override
        public int hashCode() {
            return Objects.hash(studentNumber, firstName, lastName);
        }

        @Override
        public String toString() {
            return "StudentIdentification{" + "studentNumber=" + studentNumber + ", firstName='" + firstName + '\'' + ", lastName='" + lastName + '\'' + '}';
        }
    }
}

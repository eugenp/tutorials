package com.baeldung.gson.multiplefields;

import com.google.gson.annotations.Expose;

public class ImprovedStudent extends LegacyStudent {

    @Expose
    private StudentIdentification id;

    @Expose
    private String major;

    public ImprovedStudent() {

    }

    public ImprovedStudent(StudentIdentification id, String major) {
        super(id.firstName, id.lastName, "" + id.studentNumber, major);
        this.id = id;
        this.major = major;
    }

    @Override
    public String getMajor() {
        return major;
    }

    @Override
    public void setMajor(String major) {
        this.major = major;
    }

    public StudentIdentification getId() {
        return id;
    }

    public void setId(StudentIdentification id) {
        this.id = id;
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
    }
}

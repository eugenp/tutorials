package com.baeldung.tableformatter;

public class Student {

    private int id;
    private String fullName;
    private String emailAddress;

    public Student() {

    }

    public Student(int id, String fullName, String emailAddress) {
        this.id = id;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
}

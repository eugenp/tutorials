package com.baeldung.tableformatter;

public class Student {

    private int id;
    private String fullName;
    private String emailAddress;

    public Student(int id, String fullName, String emailAddress) {
        this.id = id;
        this.fullName = fullName;
        this.emailAddress = emailAddress;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return fullName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

}

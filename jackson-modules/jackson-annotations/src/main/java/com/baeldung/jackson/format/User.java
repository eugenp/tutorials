package com.baeldung.jackson.format;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class User {
    private String firstName;
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private Date createdDate;

    public User() {
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdDate = new Date();
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

    public Date getCreatedDate() {
        return createdDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", locale = "en_GB")
    public Date getCurrentDate() {
        return new Date();
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getDateNum() {
        return new Date();
    }
}

@JsonFormat(with = JsonFormat.Feature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
class UserIgnoreCase {
    private String firstName;
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private Date createdDate;

    public UserIgnoreCase() {
    }

    public UserIgnoreCase(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.createdDate = new Date();
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

    public Date getCreatedDate() {
        return createdDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", locale = "en_GB")
    public Date getCurrentDate() {
        return new Date();
    }

    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    public Date getDateNum() {
        return new Date();
    }
}
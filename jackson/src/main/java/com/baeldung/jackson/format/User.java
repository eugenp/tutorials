package com.baeldung.jackson.format;

import java.util.Date;

import com.baeldung.jackson.domain.Person;
import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * @author Jay Sridhar
 * @version 1.0
 */
public class User extends Person {
    private String firstName;
    private String lastName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ")
    private Date createdDate;

    public User(String firstName, String lastName) {
        super(firstName, lastName);
        this.createdDate = new Date();
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

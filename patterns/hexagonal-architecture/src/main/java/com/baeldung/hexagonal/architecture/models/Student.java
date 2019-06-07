package com.baeldung.hexagonal.architecture.models;

import lombok.Data;

@Data
public class Student {

    private Integer registrationNumber;
    private String name;
    private String email;
    private String batch;

    public Student(Integer registrationNumber, String name, String email, String batch) {
        this.registrationNumber = registrationNumber;
        this.name = name;
        this.email = email;
        this.batch = batch;
    }

    public Student() {
    }

    public Integer getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(Integer registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}

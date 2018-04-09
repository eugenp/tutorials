package com.example.webfluxsample.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document()
public class Student {

    @Id
    private String id;

    private String fullName;

    private String address;

    public Student() {
        super();
    }

    public Student(String id, String fullName, String address) {
        super();
        this.id = id;
        this.fullName = fullName;
        this.address = address;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Student [id=" + id + ", fullName=" + fullName + ", address=" + address + "]";
    }

}
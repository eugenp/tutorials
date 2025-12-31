package com.example;

import java.io.Serializable;

public class User implements Serializable {

    private String name;
    private int age;

    // Nested serializable object
    private Address address;

    // Extracted serializable data
    private String traceId;

    public User(String name, int age, Address address, AuditContext auditContext) {
        this.name = name;
        this.age = age;
        this.address = address;
        this.auditContext = auditContext;

        // Extract serializable data from third-party object
        this.traceId = auditContext.getTraceId();
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", traceId='" + traceId + '\'' +
                '}';
    }
}

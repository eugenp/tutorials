package com.baeldung.java14.recordvslombok;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class StudentBuilder {
    private String firstName;
    private String lastName;
    private Long studentId;
    private String email;
    private String phoneNumber;
    private String address;
    private String country;
    private int age;
}
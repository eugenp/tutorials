package com.baeldung.instancio.student.model;


import com.baeldung.instancio.util.PrettyToString;

import java.time.LocalDate;
import java.time.Year;
import java.util.Map;
import java.util.UUID;

public class Student {
    private UUID id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private ContactInfo contactInfo;
    private EmergencyContact emergencyContact;
    private Year enrollmentYear;
    private Map<Course, Grade> courseGrades;

    public UUID getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public ContactInfo getContactInfo() {
        return contactInfo;
    }

    public EmergencyContact getEmergencyContact() {
        return emergencyContact;
    }

    public Year getEnrollmentYear() {
        return enrollmentYear;
    }

    public Map<Course, Grade> getCourseGrades() {
        return courseGrades;
    }

    @Override
    public String toString() {
        return PrettyToString.toPrettyString(this);
    }
}

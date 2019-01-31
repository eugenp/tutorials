package com.baeldung.scholarshipapproval.domain.model;

import java.time.LocalDate;
import java.util.Objects;

public class Applicant {
    private Long applicantId;
    private String firstName;
    private String lastName;
    private LocalDate applicationDate;
    private Double householdIncome;
    private Integer marks;
    private Boolean scholarshipApproved;
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Applicant applicant = (Applicant) o;
        return Objects.equals(applicantId, applicant.applicantId) &&
                Objects.equals(firstName, applicant.firstName) &&
                Objects.equals(lastName, applicant.lastName) &&
                Objects.equals(applicationDate, applicant.applicationDate) &&
                Objects.equals(householdIncome, applicant.householdIncome) &&
                Objects.equals(marks, applicant.marks) &&
                Objects.equals(scholarshipApproved, applicant.scholarshipApproved) &&
                Objects.equals(email, applicant.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicantId, firstName, lastName, applicationDate, householdIncome, marks, scholarshipApproved, email);
    }

    @Override
    public String toString() {
        return "Applicant{" +
                "applicantId=" + applicantId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", applicationDate=" + applicationDate +
                ", householdIncome=" + householdIncome +
                ", marks=" + marks +
                ", scholarshipApproved=" + scholarshipApproved +
                ", email='" + email + '\'' +
                '}';
    }

    public Long getApplicantId() {
        return applicantId;
    }

    public void setApplicantId(Long applicantId) {
        this.applicantId = applicantId;
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

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }

    public Double getHouseholdIncome() {
        return householdIncome;
    }

    public void setHouseholdIncome(Double householdIncome) {
        this.householdIncome = householdIncome;
    }

    public Integer getMarks() {
        return marks;
    }

    public void setMarks(Integer marks) {
        this.marks = marks;
    }

    public Boolean getScholarshipApproved() {
        return scholarshipApproved;
    }

    public void setScholarshipApproved(Boolean scholarshipApproved) {
        this.scholarshipApproved = scholarshipApproved;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

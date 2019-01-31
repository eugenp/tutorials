package com.baeldung.scholarshipapproval.database.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "applicant")
public class Applicant {

    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "application_date")
    private LocalDateTime applicationDate;

    @Column(name = "household_income")
    private Double householdIncome;

    @Column(name = "marks")
    private Integer marks;

    @Column(name = "email")
    private String email;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Applicant applicant = (Applicant) o;
        return Objects.equals(id, applicant.id) &&
                Objects.equals(firstName, applicant.firstName) &&
                Objects.equals(lastName, applicant.lastName) &&
                Objects.equals(applicationDate, applicant.applicationDate) &&
                Objects.equals(householdIncome, applicant.householdIncome) &&
                Objects.equals(marks, applicant.marks) &&
                Objects.equals(email, applicant.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, applicationDate, householdIncome, marks, email);
    }

    @Override
    public String toString() {
        return "Applicant{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", applicationDate=" + applicationDate +
                ", householdIncome=" + householdIncome +
                ", marks=" + marks +
                ", email='" + email + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDateTime applicationDate) {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

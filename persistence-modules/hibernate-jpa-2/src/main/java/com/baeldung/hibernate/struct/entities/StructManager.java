package com.baeldung.hibernate.struct.entities;
import jakarta.persistence.Embeddable;
import org.hibernate.annotations.Struct;

import java.io.Serializable;

@Embeddable
@Struct(name = "Department_Manager_Type", attributes = {"firstName", "lastName", "qualification"})
public class StructManager implements Serializable {
    private String firstName;
    private String lastName;
    private String qualification;

    @Override
    public String toString() {
        return "Manager{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", qualification='" + qualification + '\'' +
                '}';
    }

    public StructManager(String firstName, String lastName, String qualification) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.qualification = qualification;
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

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }
}
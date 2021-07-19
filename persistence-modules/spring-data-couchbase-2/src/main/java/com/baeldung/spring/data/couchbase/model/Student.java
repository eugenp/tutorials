package com.baeldung.spring.data.couchbase.model;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;

@Document
public class Student {
    private static final String NAME_REGEX = "^[a-zA-Z .'-]+$";

    @Id
    private String id;
    @Field
    @NotNull
    @Size(min = 1, max = 20)
    @Pattern(regexp = NAME_REGEX)
    private String firstName;
    @Field
    @NotNull
    @Size(min = 1, max = 20)
    @Pattern(regexp = NAME_REGEX)
    private String lastName;
    @Field
    @Past
    private DateTime dateOfBirth;
    @Field
    @NotNull
    private DateTime created;
    @Field
    private DateTime updated;
    @Version
    private long version;

    public Student() {
    }

    public Student(String id, String firstName, String lastName, DateTime dateOfBirth) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public DateTime getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(DateTime dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public DateTime getCreated() {
        return created;
    }

    public void setCreated(DateTime created) {
        this.created = created;
    }

    public DateTime getUpdated() {
        return updated;
    }

    public void setUpdated(DateTime updated) {
        this.updated = updated;
    }

    @Override
    public int hashCode() {
        int hash = 1;
        if (id != null) {
            hash = hash * 31 + id.hashCode();
        }
        if (firstName != null) {
            hash = hash * 31 + firstName.hashCode();
        }
        if (lastName != null) {
            hash = hash * 31 + lastName.hashCode();
        }
        if (dateOfBirth != null) {
            hash = hash * 31 + dateOfBirth.hashCode();
        }
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        if (obj == this)
            return true;
        Student other = (Student) obj;
        return this.hashCode() == other.hashCode();
    }
}
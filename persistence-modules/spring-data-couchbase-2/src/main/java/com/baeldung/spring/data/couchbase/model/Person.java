package com.baeldung.spring.data.couchbase.model;

import javax.validation.constraints.NotNull;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.couchbase.core.mapping.Document;

import com.couchbase.client.java.repository.annotation.Field;

@Document
public class Person {

    @Id
    private String id;
    @Field
    @NotNull
    private String firstName;
    @Field
    @NotNull
    private String lastName;
    @Field
    @NotNull
    private DateTime created;
    @Field
    private DateTime updated;

    public Person(String id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
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
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj == null) || (obj.getClass() != this.getClass()))
            return false;
        if (obj == this)
            return true;
        Person other = (Person) obj;
        return this.hashCode() == other.hashCode();
    }
}

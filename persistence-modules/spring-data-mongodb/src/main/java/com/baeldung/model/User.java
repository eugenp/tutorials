package com.baeldung.model;

import com.baeldung.annotation.CascadeSave;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.querydsl.core.annotations.QueryEntity;

@QueryEntity
@Document
@CompoundIndexes({ @CompoundIndex(name = "email_age", def = "{'email.id' : 1, 'age': 1}") })
public class User {

    @Id
    private String id;
    @Indexed(direction = IndexDirection.ASCENDING)
    private String name;
    @Indexed(direction = IndexDirection.ASCENDING)
    private Integer age;

    @DBRef
    @Field("email")
    @CascadeSave
    private EmailAddress emailAddress;

    @Transient
    private Integer yearOfBirth;

    public User() {
    }
    
    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    @PersistenceConstructor
    public User(final String name, @Value("#root.age ?: 0") final Integer age, final EmailAddress emailAddress) {
        this.name = name;
        this.age = age;
        this.emailAddress = emailAddress;
    }

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(final Integer age) {
        this.age = age;
    }

    public EmailAddress getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(final EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }

    public Integer getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(final Integer yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

}

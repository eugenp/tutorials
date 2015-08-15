package org.baeldung.model;

import org.baeldung.annotation.CascadeSave;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import com.mysema.query.annotations.QueryEntity;

@QueryEntity
@Document
public class User {

    @Id
    private String id;
    @Indexed(direction = IndexDirection.ASCENDING)
    private String name;

    private Integer age;

    @DBRef
    @Field("email")
    @CascadeSave
    private EmailAddress emailAddress;

    public User() {
    }

    public User(String name, Integer age, String value) {
        this.name = name;
        this.age = age;
        this.emailAddress = new EmailAddress(value);
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

    public void setEmailAddress(EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
    }
}

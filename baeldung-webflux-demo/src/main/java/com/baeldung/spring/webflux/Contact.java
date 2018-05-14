package com.baeldung.spring.webflux;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Document(collection = "contacts")
public class Contact {

    private String id;

    private String email;

    private String name;

    private Date createdDate;

    private int interactions;

    public Contact() {

    }

    public Contact(String email, String name) {

        this.email = email;
        this.name = name;
        this.id = email;
        this.createdDate = new Date();
    }

    @NotNull
    public String getEmail() {

        return email;
    }

    public void setEmail(String email) {

        this.email = email;
    }

    @NotNull
    public String getName() {

        return name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public Date getCreatedDate() {

        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {

        this.createdDate = createdDate;
    }

    @Id
    public String getId() {

        return id;
    }

    public void setId(String id) {

        this.id = id;
    }

    public int getInteractions() {

        return interactions;
    }

    public void setInteractions(int interactions) {

        this.interactions = interactions;
    }
}

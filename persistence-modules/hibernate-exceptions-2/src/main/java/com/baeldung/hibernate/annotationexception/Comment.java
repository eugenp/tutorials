package com.baeldung.hibernate.annotationexception;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Comment {

    @Id
    @GeneratedValue
    private Long id;

    private String text;

    public String getText() {
        return text;
    }

    public Comment setText(String text) {
        this.text = text;
        return this;
    }
}

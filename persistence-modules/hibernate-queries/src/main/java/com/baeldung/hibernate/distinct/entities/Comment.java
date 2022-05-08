package com.baeldung.hibernate.distinct.entities;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String text;

    public Comment() {
    }

    public Comment(String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return "Comment{" + "id=" + id + ", text='" + text + '\'' + '}';
    }
}

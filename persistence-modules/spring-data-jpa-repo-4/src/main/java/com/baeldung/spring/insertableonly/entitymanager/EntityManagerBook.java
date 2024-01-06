package com.baeldung.spring.insertableonly.entitymanager;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class EntityManagerBook {

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    public EntityManagerBook() {
    }

    public EntityManagerBook(String title) {
        this.title = title;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

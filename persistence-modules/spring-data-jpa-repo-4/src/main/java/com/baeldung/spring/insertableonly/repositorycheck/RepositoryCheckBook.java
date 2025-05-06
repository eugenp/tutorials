package com.baeldung.spring.insertableonly.repositorycheck;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class RepositoryCheckBook {

    @Id
    @GeneratedValue
    private Long id;
    private String title;

    public RepositoryCheckBook() {
    }

    public RepositoryCheckBook(String title) {
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

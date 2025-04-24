package com.baeldung.spring.insertableonly.query;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class CustomQueryBook {

    @Id
    private Long id;
    private String title;

    public CustomQueryBook() {
    }

    public CustomQueryBook(long id, String title) {
        this.id = id;
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

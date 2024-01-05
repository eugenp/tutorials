package com.baeldung.spring.insertableonly.unpadatable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UnapdatableBook {

    @Id
    @GeneratedValue
    private Long id;
    @Column(updatable = false)
    private String title;

    public UnapdatableBook() {
    }

    public UnapdatableBook(String title) {
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

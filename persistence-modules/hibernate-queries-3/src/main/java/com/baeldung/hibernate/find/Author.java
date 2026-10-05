package com.baeldung.hibernate.find;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "authors")
public class Author {
    @Id
    @Column(name = "author_id")
    private Long authorId;

    private String name;

    public Long getAuthorId() {
        return authorId;
    }

    public String getName() {
        return name;
    }
}
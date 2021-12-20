package com.baeldung.hexagonalarchitecture.infrastructure;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class JpaBookEntity {
    @Id
    private String isbn;
    private String title;

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String iSBN) {
        isbn = iSBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
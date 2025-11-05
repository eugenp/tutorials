package com.baeldung.recordswithjpa.entity;

import com.baeldung.recordswithjpa.records.BookId;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.Table;

@Entity
@Table(name = "book")
@IdClass(BookId.class)
public class CompositeBook {
    @Id
    private Long id;
    @Id
    private Long isbn;
    private String title;
    private String author;

    public CompositeBook() {
    }

    public CompositeBook(BookId bookId, String title, String author) {
        this.id = bookId.id();
        this.isbn = bookId.isbn();
        this.title = title;
        this.author = author;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIsbn() {
        return isbn;
    }

    public void setIsbn(Long isbn) {
        this.isbn = isbn;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}

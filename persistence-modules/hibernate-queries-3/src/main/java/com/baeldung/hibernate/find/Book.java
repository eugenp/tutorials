package com.baeldung.hibernate.find;

import jakarta.persistence.*;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "book_id")
    private Long bookId;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private Author author;

    public Long getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public Author getAuthor() {
        return author;
    }
}
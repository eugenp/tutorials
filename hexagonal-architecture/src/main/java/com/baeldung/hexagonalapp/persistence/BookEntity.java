package com.baeldung.hexagonalapp.persistence;

import com.baeldung.hexagonalapp.application.BookDto;

import javax.persistence.*;

@Entity
@Table(name = "book")
class BookEntity {

    protected BookEntity() {}

    public BookEntity(BookDto dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.author = dto.getAuthor();
        this.borrower = dto.getBorrower();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String author;
    private String borrower;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBorrower() {
        return borrower;
    }

    public void setBorrower(String borrower) {
        this.borrower = borrower;
    }

    public BookDto toDto() {
        return new BookDto(id, name, author, borrower);
    }
}


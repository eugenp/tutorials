package com.baeldung.hexagonalapp.core;

public class Book {
    private final Long id;
    private final String name;
    private final String author;
    private String borrower;

    public Book(Long id, String name, String author) {
        this(id, name, author, null);
    }

    public Book(Long id, String name, String author, String borrower) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.borrower = borrower;
    }

    public void lendOutTo(String borrower) {
        if (this.borrower == null) {
            this.borrower = borrower;
        } else {
            throw new BookAlreadyLentOut();
        }
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public void giveBack() {
        this.borrower = null;
    }

    public String getBorrower() {
        return borrower;
    }
}

package com.baeldung.controller;

import com.baeldung.controller.BookDetails;

public class Book {

    private BookDetails bookDetails;

    public Book() {

    }

    public Book(BookDetails bookDetails) {
        this.bookDetails = bookDetails;
    }

    public BookDetails getBookDetails() {
        return bookDetails;
    }

    public void setBookDetails(BookDetails bookDetails) {
        this.bookDetails = bookDetails;
    }

    @Override
    public String toString() {
        return "Book [Name=" + bookDetails.getNameOfTheBook() + "Author=" + bookDetails.getAuthorOfTheBook() + "]";
    }

}

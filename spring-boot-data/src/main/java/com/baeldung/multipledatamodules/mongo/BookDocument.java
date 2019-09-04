package com.baeldung.multipledatamodules.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BookDocument {
    private String bookId;
    private String bookName;
    private String bookAuthor;
    private String content;

    public BookDocument(String bookId, String bookName, String bookAuthor, String content) {
        super();
        this.bookId = bookId;
        this.bookName = bookName;
        this.bookAuthor = bookAuthor;
        this.content = content;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

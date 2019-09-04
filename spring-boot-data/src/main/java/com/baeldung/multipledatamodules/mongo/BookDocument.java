package com.baeldung.multipledatamodules.mongo;

import java.util.Objects;

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

    @Override
    public int hashCode() {
        return Objects.hash(bookAuthor, bookId, bookName, content);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (!(obj instanceof BookDocument))
            return false;
        BookDocument other = (BookDocument) obj;
        return Objects.equals(bookAuthor, other.bookAuthor) && Objects.equals(bookId, other.bookId) && Objects.equals(bookName, other.bookName) && Objects.equals(content, other.content);
    }

    @Override
    public String toString() {
        return "BookDocument [bookId=" + bookId + ", bookName=" + bookName + ", bookAuthor=" + bookAuthor + ", content=" + content + "]";
    }

}

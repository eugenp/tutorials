package com.baeldung.multipledatamodules.mongo;

import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class BookDocument {
    private String bookId;
    private String content;

    public BookDocument(String bookId, String content) {
        super();
        this.bookId = bookId;
        this.content = content;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}

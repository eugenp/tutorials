package com.baeldung.spring.convertmonoobject;

public class BookBorrowResponse {

    private String userId;
    private String bookId;
    private String status;

    public BookBorrowResponse(String userId, String bookId, String status) {
        this.userId = userId;
        this.bookId = bookId;
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

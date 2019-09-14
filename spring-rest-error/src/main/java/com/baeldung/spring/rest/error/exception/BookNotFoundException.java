package com.baeldung.spring.rest.error.exception;

@SuppressWarnings("serial")
public class BookNotFoundException extends ApiException {
    
    private long id;
    
    public BookNotFoundException(long id) {
        this.id = id;
    }

    @Override
    protected String getErrorId() {
        return "book-0001";
    }

    @Override
    protected String getErrorMessage() {
        return "Cannot find book with ID " + id;
    }

    @Override
    protected String getDetail() {
        return "Ensure that book with ID " + id + " exists";
    }
}

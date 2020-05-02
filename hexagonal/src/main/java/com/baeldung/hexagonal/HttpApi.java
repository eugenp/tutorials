package com.baeldung.hexagonal;

public class HttpApi implements ApiInterface {
    private BookService service;

    public HttpApi(BookService service) {
        this.service = service;
    }

    // TODO implement HTTP endpoint
    public Book get(String isbn) {
        return service.search(isbn);
    }
}

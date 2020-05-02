package com.baeldung.hexagonal;

public class RpcApi implements ApiInterface {
    private BookService service;

    public RpcApi(BookService service) {
        this.service = service;
    }

    public Book get(String isbn) {
        return null;
    }
}

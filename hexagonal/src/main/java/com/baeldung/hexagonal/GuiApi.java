package com.baeldung.hexagonal;

public class GuiApi implements ApiInterface {
    private BookService service;

    public GuiApi(BookService service) {
        this.service = service;
    }

    public Book get(String isbn) {
        return null;
    }
}

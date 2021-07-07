package com.baeldung.book.application.domain;

import java.util.ArrayList;
import java.util.List;

public class Book {
    String name;
    List<Page> pages;

    public Book(String name) {
        this.name = name;
        this.pages = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Page> getPages() {
        return pages;
    }

    public void addPageWithText(String text) {
        pages.add(new Page(text));
    }
}

package com.baeldung.pagination.dto;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

import com.baeldung.pagination.entity.Book;

@Getter
public class BookPage {
    private final List<Book> content;
    private final int totalPages;
    private final long totalElements;
    private final int number;
    private final int size;

    public BookPage(Page<Book> page) {
        this.content = page.getContent();
        this.totalPages = page.getTotalPages();
        this.totalElements = page.getTotalElements();
        this.number = page.getNumber();
        this.size = page.getSize();
    }
}
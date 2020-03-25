package com.bealdung.hexagonal.port;


import com.bealdung.hexagonal.core.Book;

public interface BookRepositoryPort {
    void create(String name, String author, int salary);

    Book getBook(Long userId);
}

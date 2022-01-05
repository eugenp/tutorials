package com.demo.repositories;


import com.demo.models.Book;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface Repository {

    Book save(Book s);


    Optional<Book> findById(UUID uuid);


    List<Book> findAll();


    void deleteById(UUID uuid);
}

package com.demo.repositories;


import com.demo.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Repository
public class BookRepository implements com.demo.repositories.Repository {

    @Autowired
    BookCrudRepository bookCrudRepository;

    @Override
    public Book save(Book s) {
        return bookCrudRepository.save(s);
    }

    @Override
    public Optional<Book> findById(UUID uuid) {
        return bookCrudRepository.findById(uuid);
    }

    @Override
    public List<Book> findAll() {
        return StreamSupport.stream(bookCrudRepository.findAll().spliterator(), true)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID uuid) {
        bookCrudRepository.deleteById(uuid);
    }
}

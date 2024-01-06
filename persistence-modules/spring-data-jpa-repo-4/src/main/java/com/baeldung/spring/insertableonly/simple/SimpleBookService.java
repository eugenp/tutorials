package com.baeldung.spring.insertableonly.simple;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SimpleBookService {

    private SimpleBookRepository repository;

    @Autowired
    public SimpleBookService(SimpleBookRepository repository) {
        this.repository = repository;
    }

    public SimpleBook save(SimpleBook book) {
        if (book.getId() == null) {
            return repository.save(book);
        }
        return book;
    }

    public Optional<SimpleBook> findById(Long id) {
        return repository.findById(id);
    }

}

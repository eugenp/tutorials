package com.baeldung.hexbookstore.infrastructure;

import com.baeldung.hexbookstore.core.BookStore;
import com.baeldung.hexbookstore.core.repository.BookStoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookStoreDBRepository implements BookStoreRepository {

    private final DBRepository dbRepository;

    @Autowired
    public BookStoreDBRepository(final DBRepository dbRepository) {
        this.dbRepository = dbRepository;
    }

    @Override
    public void delete(Long id) {
        BookStore book = dbRepository.findById(id).orElse(null);
        if(book != null) {
            dbRepository.delete(book);
        }
    }

    @Override
    public void save(BookStore book) {
        dbRepository.save(book);
    }

    @Override
    public List<BookStore> findAll() {
        return (List<BookStore>) dbRepository.findAll();
    }
}

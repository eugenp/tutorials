package com.baeldung.hexbookstore.core.repository;


import com.baeldung.hexbookstore.core.BookStore;

import java.util.List;

public interface BookStoreRepository {

    void save(BookStore book);

    void delete(Long id);

    List<BookStore> findAll();

}

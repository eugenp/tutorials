package com.baeldung.hexagonal.architecture.adapter;

import com.baeldung.hexagonal.architecture.core.domain.Book;
import com.baeldung.hexagonal.architecture.db.LocalStorage;
import com.baeldung.hexagonal.architecture.port.BookRepo;
import org.springframework.stereotype.Repository;

@Repository
public class BookRepoImpl implements BookRepo {

    private LocalStorage localStorage;

    public BookRepoImpl(LocalStorage localStorage){
        this.localStorage = localStorage;
    }

    @Override
    public void create(Book book) {
        localStorage.bookDB.put(book.getId(), book);
    }

    @Override
    public void delete(long id) {
        localStorage.bookDB.remove(id);
    }

    @Override
    public Book get(long id) {
        return localStorage.bookDB.get(id);
    }
}

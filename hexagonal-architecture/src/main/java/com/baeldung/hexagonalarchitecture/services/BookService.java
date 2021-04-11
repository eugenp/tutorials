package com.baeldung.hexagonalarchitecture.services;

import com.baeldung.hexagonalarchitecture.models.Book;
import com.baeldung.hexagonalarchitecture.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    @Autowired
    private BookRepository repository;

    @Autowired
    public BookService(BookRepository repo) {
        repository = repo;
    }


    public List<Book> findAllBook(){
        return repository.findAll();
    }

    public Book createBook(Book book){
        return repository.save(book);
    }

}

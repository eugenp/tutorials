package com.baeldung.spring.data.neo4j.service;


import com.baeldung.spring.data.neo4j.model.Book;
import com.baeldung.spring.data.neo4j.repostory.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    public Book save(final Book book){
       return bookRepository.save(book);
    }

    public long bookCount(){
        return bookRepository.count();
    }

    public Book findBookById(final Long id){
        return bookRepository.findOne(id);
    }

    public void delete(final long bookId){
        bookRepository.delete(bookId);
    }

    public void deleteAllInGraph(){
        bookRepository.deleteAll();
    }


}
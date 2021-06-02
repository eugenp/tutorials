package com.HexagonalArchitecture.BookApplication.Service;

import com.HexagonalArchitecture.BookApplication.Model.Book;
import com.HexagonalArchitecture.BookApplication.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    
    @Autowired
    BookRepository bookRepository;
    
    @Override
    public Book createBook(Book book) {
        
        Book newBook = bookRepository.save(book);
        return newBook;
    }
    
    @Override
    public Book getBook(String name) {
        Book book= bookRepository.findOneByName(name).orElseThrow(()-> new EntityNotFoundException("No book found with this name"));
        return book;
    }
    
    @Override
    public List<Book> listBook() {
        return bookRepository.findAll();
    }
}
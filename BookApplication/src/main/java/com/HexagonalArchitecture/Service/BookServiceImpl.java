package com.HexagonalArchitecture.Service;

import com.HexagonalArchitecture.Model.Book;
import com.HexagonalArchitecture.Repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class BookServiceImpl implements BookService{
    
    @Autowired
    BookRepository bookRepository;
    
    @Override
    public void createBook(Book book) {
        bookRepository.save(book);
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
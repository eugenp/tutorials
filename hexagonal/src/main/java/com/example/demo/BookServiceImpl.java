package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{
    BookRepository bookRepository;
    
    public BookServiceImpl(@Autowired BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    public Book getBook(String title) {
        return bookRepository.getBook(title);
    }
    
    public List<Book> getBooksByMatchingTitle(String title){
        return bookRepository.getBooksByMatchingTitle(title);
    }
    
    public List<Book> listAllBooks(){
        return bookRepository.listAllBooks();
    }
    
    public boolean addBook(Book book) {
        try{
            bookRepository.addBook(book);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
}
package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookServiceImpl implements BookService{
    @Autowired 
    BookRepository bookRepository;
    
    public Book getBook(String title) {
        return bookRepository.getBook(title);
    }
    
    public List<Book> getBooksByMatchingTitle(String title){
        return bookRepository.getBooksByMatchingTitle(title);
    }
    
    public List<Book> listAllBooks(){
        return bookRepository.listAllBooks();
    }
    
    public void addBook(Book book) {
        bookRepository.addBook(book);
    }
}
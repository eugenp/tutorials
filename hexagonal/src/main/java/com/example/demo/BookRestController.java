package com.example.demo;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/")
public class BookRestController{
    
    BookService bookService;
    
    @Autowired
    public BookRestController(BookService bookService) {
        this.bookService = bookService;
    }
    
    @GetMapping("/books/{title}")
    public Book getBook(@PathVariable("title") String title) {
        //perform pre-processing ex authN/authZ 
        return bookService.getBook(title);
    }
    
    @GetMapping("books")
    public List<Book> getBooksByMatchingTitle(@RequestParam("search_title") String title){
        return bookService.getBooksByMatchingTitle(title);
    }
    
    @PostMapping("books/")
    public void addBook(@RequestBody Book book) {
        bookService.addBook(book);
    }
}
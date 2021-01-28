package com.example.demo;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestBookService {
    
    BookService bookService;
    
    @BeforeEach
    void setup() {
        BookRepository bookRepository = new BookRepositoryListImpl();
        bookService = new BookServiceImpl(bookRepository);
    }
    
    
    @Test
    public void testBookAddAndRetireval() {
        String bookTitle = "MoneyBall";
        Book book = getDummyBook(bookTitle);
        
        bookService.addBook(book);
        
        assert bookService.getBook(bookTitle) != null;
        assert bookService.getBook(bookTitle).getTitle() == bookTitle;
    }
    
    @Test
    public void testBookSearch() {
        String bookTitle = "MoneyBall";
        Book book = getDummyBook(bookTitle);
        bookService.addBook(book);
        
        bookTitle = "FooBall";
        book = getDummyBook(bookTitle);  
        bookService.addBook(book);
        
        bookTitle = "FooBar";
        book = getDummyBook(bookTitle);
        bookService.addBook(book);
        
        assert bookService.getBooksByMatchingTitle("Ball").size() == 2;
    }
    
    private Book getDummyBook(String title) {
        Book book = new Book();
        book.setTitle(title);
        return book;
    }
    
}

class BookRepositoryListImpl implements BookRepository{
    
    List<Book> books = new ArrayList<>();
    
    public Book getBook(String title) {
        List<Book> availableBooks = books.stream().filter(book -> book.getTitle() == title).collect(Collectors.toList());
        return availableBooks.size() == 0 ? null : availableBooks.get(0);
    }
    
    public List<Book> getBooksByMatchingTitle(String title){
        return books.stream().filter(book -> book.getTitle().contains(title)).collect(Collectors.toList());
    }
    
    public List<Book> listAllBooks(){
        return null;
    }
    
    public void addBook(Book book) {
        books.add(book);
    }
}

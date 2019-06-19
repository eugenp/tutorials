package com.baeldung.hexagonalarchitecturejava;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BookAdapter {
    @Autowired
    BookServiceAdapter bookServiceAdapter;


    @PostMapping("addBook")
    public void addBook(@RequestBody Book book){
        bookServiceAdapter.addBook(book);
    }

    @DeleteMapping("deleteBook")
    public void deleteBook(@RequestParam Long bookId){
        bookServiceAdapter.removeBook(bookId);
    }

    @GetMapping("getAllBooks")
    public List<Book> getAllBooks(){
        return  bookServiceAdapter.getAllBooks();
    }


}

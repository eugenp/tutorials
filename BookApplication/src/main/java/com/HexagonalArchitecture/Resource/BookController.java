package com.HexagonalArchitecture.Resource;

import com.HexagonalArchitecture.Model.Book;
import com.HexagonalArchitecture.Service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BookController {
    
    @Autowired
    private BookService bookService;
    
    @PostMapping(value = "book", produces = MediaType.APPLICATION_JSON_VALUE)
    public void createBook(@RequestBody Book book)
    {
        bookService.createBook(book);
    }
    
    @GetMapping(value = "book/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Book getBook(@PathVariable String name)
    {
        return bookService.getBook(name);
    }
    
    @GetMapping(value = "book-list", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Book> listBook()
    {
        System.out.println("This is list of books");
        return bookService.listBook();
    }
}

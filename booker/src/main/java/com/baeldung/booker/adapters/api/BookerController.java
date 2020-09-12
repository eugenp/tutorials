package com.baeldung.booker.adapters.api;

import com.baeldung.booker.domain.Book;
import com.baeldung.booker.services.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookerController {

  @Autowired
  BookService bookService;

  @GetMapping({"/",""})
  public List<Book> getBookByCategory() {
    return bookService.findAll();
  }

  @GetMapping({"/return/{id}"})
  public Book returnBook(@PathVariable Long id) {
    Book book = bookService.returnABook(id);
    return book;
  }

  @GetMapping({"/rent/{id}"})
  public Book rentBook(@PathVariable Long id) {
    return bookService.rentBook(id);
  }

}

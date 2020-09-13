package com.baeldung.booker.adapters.api;

import com.baeldung.booker.domain.Book;
import com.baeldung.booker.ports.incoming.IBrowseBooks;
import com.baeldung.booker.ports.incoming.IRentBook;
import com.baeldung.booker.ports.incoming.IReturnBook;
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
  IRentBook iRentBookAdapter;

  @Autowired
  IReturnBook iReturnBookAdapter;

  @Autowired
  IBrowseBooks iBrowseBooksAdapter;

  @GetMapping({"/",""})
  public List<Book> getBookByCategory() {
    return iBrowseBooksAdapter.findAll();
  }

  @GetMapping({"/return/{id}"})
  public Book returnBook(@PathVariable Long id) {
    Book book = iReturnBookAdapter.returnABook(id);
    return book;
  }

  @GetMapping({"/rent/{id}"})
  public Book rentBook(@PathVariable Long id) {
    return iRentBookAdapter.rentBook(id);
  }

}

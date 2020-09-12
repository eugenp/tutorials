package com.baeldung.booker.services;

import com.baeldung.booker.domain.Book;
import com.baeldung.booker.ports.incoming.IRentBook;
import com.baeldung.booker.ports.incoming.IReturnBook;
import com.baeldung.booker.ports.outgoing.IFindBook;
import com.baeldung.booker.ports.outgoing.ISaveBook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService implements IRentBook, IReturnBook {

  private IFindBook findBookPort;
  private ISaveBook saveBookPort;

  public BookService(IFindBook findBookPort, ISaveBook saveBookPort) {
    this.findBookPort = findBookPort;
    this.saveBookPort = saveBookPort;
  }

  @Override
  public Book rentBook(Long id) {
    Book book = findBookPort.findById(id);
    if (book.rentABook()) {
      return saveBookPort.saveBook(book);
    }
    return book;
  }

  @Override
  public Book returnABook(Long id) {
    Book book = findBookPort.findById(id);
    book.returnABook();
    return saveBookPort.saveBook(book);
  }

  public List<Book> findAll(){
    return findBookPort.findAll();
  }

}

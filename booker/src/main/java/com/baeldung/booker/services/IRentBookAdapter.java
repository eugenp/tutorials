package com.baeldung.booker.services;

import com.baeldung.booker.domain.Book;
import com.baeldung.booker.ports.incoming.IRentBook;
import com.baeldung.booker.ports.outgoing.IFindBook;
import com.baeldung.booker.ports.outgoing.ISaveBook;
import org.springframework.stereotype.Service;

@Service
public class IRentBookAdapter implements IRentBook {

  private IFindBook findBookPort;
  private ISaveBook saveBookPort;

  public IRentBookAdapter(IFindBook findBookPort, ISaveBook saveBookPort) {
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

}

package com.baeldung.booker.services;

import com.baeldung.booker.domain.Book;
import com.baeldung.booker.ports.incoming.IReturnBook;
import com.baeldung.booker.ports.outgoing.IFindBook;
import com.baeldung.booker.ports.outgoing.ISaveBook;
import org.springframework.stereotype.Service;

@Service
public class IReturnBookAdapter implements IReturnBook {

  private IFindBook findBookPort;
  private ISaveBook saveBookPort;

  IReturnBookAdapter(IFindBook findBookPort, ISaveBook saveBookPort) {
    this.findBookPort = findBookPort;
    this.saveBookPort = saveBookPort;
  }
  @Override
  public Book returnABook(Long id) {
    Book book = findBookPort.findById(id);
    book.returnABook();
    return saveBookPort.saveBook(book);
  }
}

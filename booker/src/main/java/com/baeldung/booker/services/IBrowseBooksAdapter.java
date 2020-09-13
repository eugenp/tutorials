package com.baeldung.booker.services;

import com.baeldung.booker.domain.Book;
import com.baeldung.booker.ports.incoming.IBrowseBooks;
import com.baeldung.booker.ports.outgoing.IFindBook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IBrowseBooksAdapter implements IBrowseBooks {

  private IFindBook findBookPort;

  public IBrowseBooksAdapter(IFindBook findBookPort) {
    this.findBookPort = findBookPort;
  }


  public List<Book> findAll(){
    return findBookPort.findAll();
  }
}

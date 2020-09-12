package com.baeldung.booker.ports.outgoing;

import com.baeldung.booker.domain.Book;

public interface ISaveBook {
  Book saveBook(Book book);
}

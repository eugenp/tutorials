package com.baeldung.booker.ports.outgoing;

import com.baeldung.booker.domain.Book;

import java.util.List;

public interface IFindBook {
  Book findById(Long id);
  List<Book> findAll();
}

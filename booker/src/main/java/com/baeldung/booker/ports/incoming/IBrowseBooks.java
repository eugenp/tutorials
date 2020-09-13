package com.baeldung.booker.ports.incoming;

import com.baeldung.booker.domain.Book;

import java.util.List;

public interface IBrowseBooks {

  List<Book> findAll();
}

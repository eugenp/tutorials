package com.baeldung.booker.ports.incoming;

import com.baeldung.booker.domain.Book;

public interface IRentBook {
  Book rentBook(Long id);
}

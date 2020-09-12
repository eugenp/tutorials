package com.baeldung.booker.ports.incoming;

import com.baeldung.booker.domain.Book;

public interface IReturnBook {
  Book returnABook(Long id);
}

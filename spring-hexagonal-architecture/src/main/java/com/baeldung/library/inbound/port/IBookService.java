package com.baeldung.library.inbound.port;

import com.baeldung.library.domain.Book;

public interface IBookService {
    void publishBook(Book book);
}

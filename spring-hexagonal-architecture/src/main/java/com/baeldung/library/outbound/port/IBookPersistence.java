package com.baeldung.library.outbound.port;

import com.baeldung.library.domain.Book;

public interface IBookPersistence {
    void publishBook(Book book);
}

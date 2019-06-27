package com.baeldung.hexagonal.bookfinder.port;

import com.baeldung.hexagonal.bookfinder.domain.Book;

public interface BookLocator {

    Book findBookWithPassage(String passage);
}

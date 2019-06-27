package com.baeldung.hexagonal.bookfinder.port;

import com.baeldung.hexagonal.bookfinder.domain.Store;

public interface BookFinder {

    Store findStoreForBookWithPassage(String passage);
}

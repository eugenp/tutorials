package com.baeldung.book.application.port;

import com.baeldung.book.application.domain.Page;

import java.util.List;

public interface GetPages {
    List<Page> fromBook(String name);
}

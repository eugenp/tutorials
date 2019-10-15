package com.baeldung.hexagonalarchitecture.web;

import com.baeldung.hexagonalarchitecture.core.domain.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

public interface BookUI {
    @GetMapping("/books")
    Book getBook(@RequestParam Long id);
}

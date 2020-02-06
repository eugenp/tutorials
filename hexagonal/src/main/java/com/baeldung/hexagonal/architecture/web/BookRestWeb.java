package com.baeldung.hexagonal.architecture.web;

import com.baeldung.hexagonal.architecture.core.domain.Book;
import org.springframework.web.bind.annotation.*;

public interface BookRestWeb {

    @PostMapping
    void create(@RequestBody Book book);

    @DeleteMapping ("/{id}")
    void delete(@PathVariable long id);

    @GetMapping("/{id}")
    Book get(@PathVariable long id);

}

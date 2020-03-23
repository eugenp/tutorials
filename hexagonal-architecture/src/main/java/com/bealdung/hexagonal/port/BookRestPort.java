package com.bealdung.hexagonal.port;

import com.bealdung.hexagonal.core.Book;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface BookRestPort {
    @PostMapping("create")
    void create(@RequestBody Book request);

    @GetMapping("view/{id}")
    Book view(@PathVariable Long userId);
}

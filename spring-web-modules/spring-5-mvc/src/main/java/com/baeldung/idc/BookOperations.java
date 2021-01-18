package com.baeldung.idc;

import java.util.List;
import java.util.Optional;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/default")
public interface BookOperations {

    @GetMapping("/")
    List<Book> getAll();

    @GetMapping("/{id}")
    Optional<Book> getById(@PathVariable int id);

    @PostMapping("/save/{id}")
    public void save(@RequestBody Book book, @PathVariable int id);
}

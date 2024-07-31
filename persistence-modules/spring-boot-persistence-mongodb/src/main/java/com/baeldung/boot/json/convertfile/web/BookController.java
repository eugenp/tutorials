package com.baeldung.boot.json.convertfile.web;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.baeldung.boot.json.convertfile.ImportUtils;
import com.baeldung.boot.json.convertfile.dao.BookRepository;
import com.baeldung.boot.json.convertfile.data.Book;
import com.baeldung.boot.json.convertfile.service.ImportJsonService;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookRepository books;

    @Autowired
    private ImportJsonService service;

    @PostMapping
    public Book postBook(@RequestBody Book book) throws IOException {
        return books.insert(book);
    }

    @GetMapping
    public List<Book> getBooks() {
        return books.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Book> getBook(@PathVariable String id) {
        return books.findById(id);
    }

    @PostMapping("/import/file")
    public String postJsonFile(@RequestPart("parts") MultipartFile jsonStringsFile) throws IOException {
        List<String> jsonLines = ImportUtils.lines(jsonStringsFile);
        return service.importTo(Book.class, jsonLines);
    }
}

package com.baeldung.queryparamdoc;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/books")
public class BookController {

    @GetMapping
    public List<Book> getBooks(@RequestParam(name = "page", required = false) Integer page) {
        return new ArrayList<>();
    }
}

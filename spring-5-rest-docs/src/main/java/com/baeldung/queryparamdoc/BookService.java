package com.baeldung.queryparamdoc;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    @SuppressWarnings("unused")
    public List<Book> getBooks(Integer page) {
        return new ArrayList<>();
    }
}

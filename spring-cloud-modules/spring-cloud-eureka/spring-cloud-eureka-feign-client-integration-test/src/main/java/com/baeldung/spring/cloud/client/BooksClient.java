package com.baeldung.spring.cloud.client;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import com.baeldung.spring.cloud.model.Book;

@FeignClient(name = "books-service")
public interface BooksClient {

    @RequestMapping("/books")
    List<Book> getBooks();
}

package com.baeldung.spring.cloud.client;

import com.baeldung.spring.cloud.model.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("books-service")
//@FeignClient(value="simple-books-client", url="${book.service.url}")
public interface BooksClient {

    @RequestMapping("/books")
    List<Book> getBooks();

}

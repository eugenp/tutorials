package com.baeldung.spring.cloud.client;

import com.baeldung.spring.cloud.model.Book;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

//@FeignClient(value="simple-books-client", url="${book.service.url}")
@FeignClient("books-client")
public interface BooksClient {

    @RequestMapping("/books")
    List<Book> getBooks();

}

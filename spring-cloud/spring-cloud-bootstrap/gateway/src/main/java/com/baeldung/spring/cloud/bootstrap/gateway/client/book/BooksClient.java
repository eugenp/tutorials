package com.baeldung.spring.cloud.bootstrap.gateway.client.book;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(value = "book-service")
public interface BooksClient {
    @RequestMapping(method = RequestMethod.GET, value="/books/{bookId}")
    Book getBookById(@PathVariable("bookId") Long bookId, @RequestHeader("Cookie") String session);
}

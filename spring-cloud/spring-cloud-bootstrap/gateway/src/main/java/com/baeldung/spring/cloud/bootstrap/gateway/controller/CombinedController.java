package com.baeldung.spring.cloud.bootstrap.gateway.controller;

import com.baeldung.spring.cloud.bootstrap.gateway.client.book.Book;
import com.baeldung.spring.cloud.bootstrap.gateway.client.book.BooksClient;
import com.baeldung.spring.cloud.bootstrap.gateway.client.rating.Rating;
import com.baeldung.spring.cloud.bootstrap.gateway.client.rating.RatingsClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/combined")
public class CombinedController {

    private final BooksClient booksClient;
    private final RatingsClient ratingsClient;

    @Autowired
    public CombinedController(BooksClient booksClient, RatingsClient ratingsClient) {
        this.booksClient = booksClient;
        this.ratingsClient = ratingsClient;
    }

    @GetMapping
    public Book getCombinedResponse(@RequestParam Long bookId, @CookieValue("SESSION") String session){
        Book book = booksClient.getBookById(bookId);
        List<Rating> ratings = ratingsClient.getRatingsByBookId(bookId, "SESSION="+session);
        book.setRatings(ratings);
        return book;
    }
}

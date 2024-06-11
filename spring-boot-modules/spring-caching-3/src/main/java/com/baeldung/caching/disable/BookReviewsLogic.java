package com.baeldung.caching.disable;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class BookReviewsLogic {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private CacheManager cacheManager;

    @Cacheable(value = "book_reviews", key = "#isbn")
    public List<BookReview> getBooksByIsbn(String isbn) {
        return bookRepository.findByIsbn(isbn);
    }
}

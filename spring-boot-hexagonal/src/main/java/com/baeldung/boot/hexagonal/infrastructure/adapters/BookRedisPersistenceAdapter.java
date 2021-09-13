package com.baeldung.boot.hexagonal.infrastructure.adapters;

import com.baeldung.boot.hexagonal.domain.models.Book;
import com.baeldung.boot.hexagonal.domain.ports.out.BookPersistence;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Component
@RequiredArgsConstructor
@Slf4j
public class BookRedisPersistenceAdapter implements BookPersistence {

    private static final String HASH_KEY = "Books";
    private final RedisTemplate<Object, Object> redisTemplate;
    private final ObjectMapper objectMapper;
    private final ModelMapper modelMapper;

    @Override
    public void addBook(Book book) {
        log.info("Adding the book to Redis");
        if (book.getId() == null || book.getId() == 0)
            book.setId(ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE));

        redisTemplate.opsForHash().put(HASH_KEY, String.valueOf(book.getId()), book);
    }

    @Override
    public Book getBook(long id) {
        log.info("Fetching the book from Redis ");
        HashMap<String, String> hashMap = (HashMap<String, String>) redisTemplate.opsForHash().get(HASH_KEY, String.valueOf(id));
        return objectMapper.convertValue(hashMap, Book.class);
    }

    @Override
    public List<Book> getBooks() {
        log.info("Fetching all the book from Redis ");
        Map<Object, Object> bookEntries = redisTemplate.opsForHash().entries(HASH_KEY);
        List<Book> bookList = new ArrayList<>();
        bookEntries.forEach((k, v) -> bookList.add(modelMapper.map(v, Book.class)));
        return bookList;
    }

    @Override
    public void deleteBook(long id) {
        log.info("Delete the book in Redis ");
        redisTemplate.opsForHash().delete(HASH_KEY, String.valueOf(id));
    }

    @Override
    public void updateBook(Book book) {
        log.info("Update the book in Redis ");
        // TODO: Code to update the book in Redis goes here
    }
}

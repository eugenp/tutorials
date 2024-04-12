package com.baeldung.spring.redis.configuration.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import com.baeldung.spring.redis.configuration.entity.Book;

@Component
public class BooksRepository {

    @Autowired
    private RedisTemplate<Long, Book> redisTemplate;

    public void save(Book book) {
        redisTemplate.opsForValue()
            .set(book.getId(), book);
    }

    public Book findById(Long id) {
        return redisTemplate.opsForValue()
            .get(id);
    }

}

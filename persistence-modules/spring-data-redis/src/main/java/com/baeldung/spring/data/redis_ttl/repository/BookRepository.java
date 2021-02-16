package com.baeldung.spring.data.redis_ttl.repository;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.baeldung.spring.data.redis_ttl.entity.Book;

@Repository
public class BookRepository{

    private RedisTemplate<String, Book> redisTemplate;
    
    @Autowired
    public BookRepository(RedisTemplate<String, Book> redisTemplate) {
      this.redisTemplate = redisTemplate;
    }


    public Book get(String title) {
        return redisTemplate.opsForValue().get(title);
    }
    
    public void save(Book book) {
        redisTemplate.opsForValue().set(book.getTitle(), book);
        
        int ttlValue = 5; //secs 
        if(book.getLanguage().toLowerCase().equals("english"))
            ttlValue = 10;
        
        redisTemplate.expire(book.getTitle(), ttlValue, TimeUnit.SECONDS);
    }
}
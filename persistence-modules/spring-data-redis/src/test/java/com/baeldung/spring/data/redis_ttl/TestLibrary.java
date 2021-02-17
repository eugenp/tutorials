package com.baeldung.spring.data.redis_ttl;

import java.io.IOException;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.spring.data.redis_ttl.entity.Book;
import com.baeldung.spring.data.redis_ttl.entity.Librarian;
import com.baeldung.spring.data.redis_ttl.repository.BookRepository;
import com.baeldung.spring.data.redis_ttl.repository.LibrarianRepository;

import redis.embedded.RedisServerBuilder;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = LibraryApplication.class)
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class TestLibrary {
    
    private static redis.embedded.RedisServer redisServer;
    
    @BeforeClass
    public static void startRedisServer() throws IOException {
        redisServer = new RedisServerBuilder().port(6379).setting("maxmemory 256M").build();
        redisServer.start();
    }
    
    @AfterClass
    public static void stopRedisServer() throws IOException {
        redisServer.stop();
    }    
    
    @Autowired
    BookRepository bookRepository;
    
    
    @Autowired
    LibrarianRepository librarianRepository;

    @Test
    public void testSpanishBookRetrieval() throws InterruptedException {
        Book book = getDummyBook("Don Quixote", "Spanish");
        bookRepository.save(book);
        
        assert bookRepository.get(book.getTitle()).getTitle()
          .equals(book.getTitle());
        
        Thread.sleep(6000);
        
        assert bookRepository.get(book.getTitle()) == null;
    }
    
    @Test
    public void testEnglishBookRetrieval() throws InterruptedException {
        Book book = getDummyBook("MoneyBall", "English");
        bookRepository.save(book);
        
        Thread.sleep(7000);
        assert bookRepository.get(book.getTitle()).getTitle()
          .equals(book.getTitle());
        
        Thread.sleep(3000);
        // key has been deleted
        assert bookRepository.get(book.getTitle()) == null;
    }
    
    @Test
    public void testLibrarianInfo() throws InterruptedException {
        Librarian librarian = new Librarian(5L, "Joe");
        librarianRepository.save(librarian);
        
        assert librarianRepository.findById(librarian.getId()).get().getName()
          .equals(librarian.getName());
        
        Thread.sleep(6000);
        // key has been deleted
        assert librarianRepository.findById(librarian.getId()).isPresent() == false;
    }

    
    private Book getDummyBook(String title, String lang) {
        Book book = new Book(title, lang);
        return book;
    }
    
}

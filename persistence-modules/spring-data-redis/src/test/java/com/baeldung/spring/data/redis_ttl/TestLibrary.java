package com.baeldung.spring.data.redis_ttl;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.baeldung.spring.data.redis_ttl.entity.Book;
import com.baeldung.spring.data.redis_ttl.repository.BookRepository;

@SpringBootTest(classes = TestRedisConfiguration.class)
public class TestLibrary {
    
    @Autowired
    BookRepository bookRepository;
    

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

    
    private Book getDummyBook(String title, String lang) {
        Book book = new Book(title, lang);
        return book;
    }
    
}

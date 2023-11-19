package com.baeldung.spring.data.jpa.listrepositories.repository;

import com.baeldung.spring.data.jpa.listrepositories.entity.Book;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

@SpringBootTest
public class BookListRepositoryIntegrationTest {

    @Autowired
    private BookListRepository bookListRepository;

    @Test
    public void givenDbContainsBooks_whenFindBooksByAuthor_thenReturnBooksByAuthor() {
        Book book1 = new Book("Spring Data", "John Doe", "1234567890");
        Book book2 = new Book("Spring Data 2", "John Doe", "1234567891");
        Book book3 = new Book("Spring Data 3", "John Doe", "1234567892");
        bookListRepository.saveAll(Arrays.asList(book1, book2, book3));

        List<Book> books = bookListRepository.findBooksByAuthor("John Doe");
        Assertions.assertEquals(3, books.size());
    }
}
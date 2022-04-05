package com.baeldung.boot.readonlyrepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.NoSuchElementException;

@SpringBootTest( classes = ReadOnlyRepositoryApplication.class )
public class ReadOnlyRepositoryUnitTest
{
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookReadOnlyRepository bookReadOnlyRepository;

    @Test
    public void givenBooks_whenUsingReadOnlyRepository_thenGetThem() {
        Book aChristmasCarolCharlesDickens = new Book();
        aChristmasCarolCharlesDickens.setTitle("A Christmas Carol");
        aChristmasCarolCharlesDickens.setAuthor("Charles Dickens");
        bookRepository.save(aChristmasCarolCharlesDickens);

        Book greatExpectationsCharlesDickens = new Book();
        greatExpectationsCharlesDickens.setTitle("Great Expectations");
        greatExpectationsCharlesDickens.setAuthor("Charles Dickens");
        bookRepository.save(greatExpectationsCharlesDickens);

        Book greatExpectationsKathyAcker = new Book();
        greatExpectationsKathyAcker.setTitle("Great Expectations");
        greatExpectationsKathyAcker.setAuthor("Kathy Acker");
        bookRepository.save(greatExpectationsKathyAcker);

        List<Book> charlesDickensBooks = bookReadOnlyRepository.findByAuthor("Charles Dickens");
        Assertions.assertEquals(2, charlesDickensBooks.size());

        List<Book> greatExpectationsBooks = bookReadOnlyRepository.findByTitle("Great Expectations");
        Assertions.assertEquals(2, greatExpectationsBooks.size());

        List<Book> allBooks = bookReadOnlyRepository.findAll();
        Assertions.assertEquals(3, allBooks.size());

        Long bookId = allBooks.get(0).getId();
        Book book = bookReadOnlyRepository.findById(bookId).orElseThrow(NoSuchElementException::new);
        Assertions.assertNotNull(book);
    }
}

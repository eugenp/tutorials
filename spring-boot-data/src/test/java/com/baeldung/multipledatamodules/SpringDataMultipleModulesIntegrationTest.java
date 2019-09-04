package com.baeldung.multipledatamodules;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.StreamSupport;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.multipledatamodules.jpa.Book;
import com.baeldung.multipledatamodules.jpa.BookRepository;
import com.baeldung.multipledatamodules.redis.BookLoan;
import com.baeldung.multipledatamodules.redis.BookLoanRepository;

@RunWith(SpringRunner.class) 
@SpringBootTest(classes = { RedisTestConfiguration.class ,SpringDataMultipleModules.class, })
public class SpringDataMultipleModulesIntegrationTest {

    private static Logger LOGGER = LoggerFactory.getLogger(SpringDataMultipleModulesIntegrationTest.class);

    @Autowired
    private ApplicationContext context;

    @Autowired
    private BookRepository bookRepo;

    @Autowired
    private BookLoanRepository bookLoanRepo;

    @Test
    public void givenAutoconfiguration_whenStarting_thenSuccess() {
    }

    @Test
    public void givenBook_whenPersist_thenSuccess() {
        // given
        Book book = new Book(UUID.randomUUID()
            .toString(), "Foundation", "Asimov");

        // when
        bookRepo.save(book);

        // then
        List<Book> result = bookRepo.findAll();
        assertThat(result.isEmpty(), is(false));
        assertThat(result.contains(book), is(true));
        LOGGER.info(result.get(0)
            .toString());
    }

    @Test
    public void givenBookLoan_whenPersist_thenSuccess() {
        // given
        BookLoan bookLoan = new BookLoan(UUID.randomUUID()
            .toString(), "Baeldung",
            Instant.ofEpochSecond(100)
                .toString(),
            Instant.ofEpochSecond(250)
                .toString());
       
        // when
        bookLoanRepo.save(bookLoan);
        
        // then
        Iterable<BookLoan> result = bookLoanRepo.findAll();
        
        long resultSize = StreamSupport.stream(result.spliterator(), false).count();
        assertThat(resultSize, is(1L));
    }
    
    
    
    
    
    
    
    
    
    
}

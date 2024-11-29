package com.baeldung.sequencenaming;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

import java.sql.Connection;
import java.util.List;

import javax.sql.DataSource;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(properties = "spring.config.location=classpath:application-test.properties")
public class PersonRepositoryIntegrationTest {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void givenNamingStrategy_whenSavingPerson_thenSequenceIsCreatedWithSpecifiedNamingStrategy() {
        // Change the naming strategy in properties file to see the differences.

        Person person = new Person();
        person.setName("John Doe");
        personRepository.save(person);

        List<Person> personList = personRepository.findAll();

        assertTrue(personList.size() > 0);
    }

    @Test
    void givenSingleNamingStrategy_whenSavingPersonAndBook_thenUsesSameSequenceForBoth() {
        // Change the naming strategy in properties file to see the differences.

        Person person = new Person();
        person.setName("John Doe");
        personRepository.save(person);

        Book book = new Book();
        book.setTitle("Baeldung");
        bookRepository.save(book);

        List<Person> personList = personRepository.findAll();
        List<Book> bookList = bookRepository.findAll();

        assertEquals((long)1,(long) personList.get(0).getId());
        assertEquals((long)2, (long)bookList.get(0).getId());
    }

}

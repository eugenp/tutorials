package com.baeldung.multipledatamodules;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.baeldung.multipledatamodules.cassandra.BookAudit;
import com.baeldung.multipledatamodules.cassandra.BookAuditCrudRepository;
import com.baeldung.multipledatamodules.cassandra.BookAuditRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringDataMultipleModules.class })
public class MultipleDataModulesCassandraIntegrationTest {

    private static Logger LOGGER = LoggerFactory.getLogger(MultipleDataModulesCassandraIntegrationTest.class);

    @Autowired
    private BookAuditRepository bookAuditRepository;

    @Autowired
    private BookAuditCrudRepository bookAuditCrudRepository;

    @After
    public void cleanup() {
        bookAuditRepository.deleteAll();
        bookAuditCrudRepository.deleteAll();
    }

    @Test
    public void givenBookAudit_whenPersistWithBookAuditRepository_thenSuccess() {

        // given
        BookAudit bookAudit = new BookAudit("lorem", "ipsum", "Baeldung", "19:30/20.08.2017");

        // when
        bookAuditRepository.save(bookAudit);

        // then
        List<BookAudit> result = bookAuditRepository.findAll();
        assertThat(result.isEmpty(), is(false));
        assertThat(result.contains(bookAudit), is(true));
        LOGGER.info(result.get(0).toString());
    }

    @Test
    public void givenBookAudit_whenPersistWithBookAuditCrudRepository_thenSuccess() {

        // given
        BookAudit bookAudit = new BookAudit("lorem", "ipsum", "Baeldung", "19:30/20.08.2017");

        // when
        bookAuditCrudRepository.save(bookAudit);

        // then
        Iterable<BookAudit> resultIterable = bookAuditCrudRepository.findAll();
        List<BookAudit> result = StreamSupport.stream(resultIterable.spliterator(), false)
                                              .collect(Collectors.toList());
        assertThat(result.isEmpty(), is(false));
        assertThat(result.contains(bookAudit), is(true));
        LOGGER.info(result.get(0).toString());
    }

}

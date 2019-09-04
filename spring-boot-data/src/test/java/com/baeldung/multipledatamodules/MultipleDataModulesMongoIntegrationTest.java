package com.baeldung.multipledatamodules;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.UUID;
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

import com.baeldung.multipledatamodules.mongo.BookDocument;
import com.baeldung.multipledatamodules.mongo.BookDocumentCrudRepository;
import com.baeldung.multipledatamodules.mongo.BookDocumentRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { SpringDataMultipleModules.class })
public class MultipleDataModulesMongoIntegrationTest {

    private static Logger LOGGER = LoggerFactory.getLogger(MultipleDataModulesMongoIntegrationTest.class);

    @Autowired
    private BookDocumentRepository bookDocumentRepository;

    @Autowired
    private BookDocumentCrudRepository bookDocumentCrudRepository;

    @After
    public void cleanup() {
        bookDocumentRepository.deleteAll();
        bookDocumentCrudRepository.deleteAll();
    }

    @Test
    public void givenBookDocument_whenPersistWithBookDocumentRepository_thenSuccess() {
        // given
        BookDocument bookDocument = new BookDocument(UUID.randomUUID()
            .toString(), "Foundation", "Isaac Asimov", "Once upon a time ...");

        // when
        bookDocumentRepository.save(bookDocument);

        // then
        List<BookDocument> result = bookDocumentRepository.findAll();
        assertThat(result.isEmpty(), is(false));
        assertThat(result.contains(bookDocument), is(true));
        LOGGER.info(result.get(0)
            .toString());
    }

    @Test
    public void givenBookAudit_whenPersistWithBookDocumentCrudRepository_thenSuccess() {
        // given
        BookDocument bookDocument = new BookDocument(UUID.randomUUID()
            .toString(), "Foundation", "Isaac Asimov", "Once upon a time ...");

        // when
        bookDocumentCrudRepository.save(bookDocument);

        // then
        Iterable<BookDocument> resultIterable = bookDocumentCrudRepository.findAll();
        List<BookDocument> result = StreamSupport.stream(resultIterable.spliterator(), false)
            .collect(Collectors.toList());
        assertThat(result.isEmpty(), is(false));
        assertThat(result.contains(bookDocument), is(true));
        LOGGER.info(result.get(0)
            .toString());
    }

}

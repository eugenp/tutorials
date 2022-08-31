package com.baeldung.uuid;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.baeldung.uuid.config.CustomRepositoryMongoConfig;
import com.baeldung.uuid.model.Book;
import com.baeldung.uuid.repository.BookRepository;

/**
 * 
 * This test requires:
 * * mongodb instance running on the environment
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CustomRepositoryMongoConfig.class)
public class CustomRepositoryLiveTest {

    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private MongoOperations mongoOps;

    @Before
    public void testSetup() {
        if (!mongoOps.collectionExists(Book.class)) {
            mongoOps.createCollection(Book.class);
        }
    }

    @After
    public void tearDown() {
        mongoOps.dropCollection(Book.class);
    }

    @Test
    public void whenInsertingBook_thenBookIsInserted() {
        final Book book = new Book();
        book.setTitle("The Lord of the Rings");
        book.setAuthor("JRR Tolkien");
        Book savedBook = bookRepository.save(book);
                
        Book result = mongoOps.findOne(Query.query(Criteria.where("_id").is(savedBook.getId())), Book.class);
                
        assertEquals(result.getTitle(), "The Lord of the Rings");
    }
}

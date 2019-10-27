package com.baeldung.morphia;

import static dev.morphia.aggregation.Group.grouping;
import static dev.morphia.aggregation.Group.push;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import com.baeldung.morphia.domain.Author;
import com.baeldung.morphia.domain.Book;
import com.baeldung.morphia.domain.Publisher;
import com.mongodb.MongoClient;

import dev.morphia.Datastore;
import dev.morphia.Morphia;
import dev.morphia.query.Query;
import dev.morphia.query.UpdateOperations;

@Ignore
public class MorphiaIntegrationTest {

    private static Datastore datastore;
    private static ObjectId id = new ObjectId();

    @BeforeClass
    public static void setUp() {
        Morphia morphia = new Morphia();
        morphia.mapPackage("com.baeldung.morphia");
        datastore = morphia.createDatastore(new MongoClient(), "library");
        datastore.ensureIndexes();
    }

    @Test
    public void givenDataSource_whenCreateEntity_thenEntityCreated() {
        Publisher publisher = new Publisher(id, "Awsome Publisher");
        Book book = new Book("9781565927186", "Learning Java", "Tom Kirkman", 3.95, publisher);
        Book companionBook = new Book("9789332575103", "Java Performance Companion", "Tom Kirkman", 1.95, publisher);
        book.addCompanionBooks(companionBook);
        datastore.save(companionBook);
        datastore.save(book);

        List<Book> books = datastore.createQuery(Book.class)
            .field("title")
            .contains("Learning Java")
            .find()
            .toList();
        assertEquals(1, books.size());
        assertEquals(book, books.get(0));
    }

    @Test
    public void givenDocument_whenUpdated_thenUpdateReflected() {
        Publisher publisher = new Publisher(id, "Awsome Publisher");
        Book book = new Book("9781565927186", "Learning Java", "Tom Kirkman", 3.95, publisher);
        datastore.save(book);
        Query<Book> query = datastore.createQuery(Book.class)
            .field("title")
            .contains("Learning Java");
        UpdateOperations<Book> updates = datastore.createUpdateOperations(Book.class)
            .inc("price", 1);
        datastore.update(query, updates);
        List<Book> books = datastore.createQuery(Book.class)
            .field("title")
            .contains("Learning Java")
            .find()
            .toList();
        assertEquals(4.95, books.get(0)
            .getCost());
    }

    @Test
    public void givenDocument_whenDeleted_thenDeleteReflected() {
        Publisher publisher = new Publisher(id, "Awsome Publisher");
        Book book = new Book("9781565927186", "Learning Java", "Tom Kirkman", 3.95, publisher);
        datastore.save(book);
        Query<Book> query = datastore.createQuery(Book.class)
            .field("title")
            .contains("Learning Java");
        datastore.delete(query);
        List<Book> books = datastore.createQuery(Book.class)
            .field("title")
            .contains("Learning Java")
            .find()
            .toList();
        assertEquals(0, books.size());
    }

    @Test
    public void givenDocument_whenAggregated_thenResultsCollected() {
        Publisher publisher = new Publisher(id, "Awsome Publisher");
        datastore.save(new Book("9781565927186", "Learning Java", "Tom Kirkman", 3.95, publisher));
        datastore.save(new Book("9781449313142", "Learning Perl", "Mark Pence", 2.95, publisher));
        datastore.save(new Book("9787564100476", "Learning Python", "Mark Pence", 5.95, publisher));
        datastore.save(new Book("9781449368814", "Learning Scala", "Mark Pence", 6.95, publisher));
        datastore.save(new Book("9781784392338", "Learning Go", "Jonathan Sawyer", 8.95, publisher));

        Iterator<Author> authors = datastore.createAggregation(Book.class)
            .group("author", grouping("books", push("title")))
            .out(Author.class);

        assertTrue(authors.hasNext());

    }

    @Test
    public void givenDocument_whenProjected_thenOnlyProjectionReceived() {
        Publisher publisher = new Publisher(id, "Awsome Publisher");
        Book book = new Book("9781565927186", "Learning Java", "Tom Kirkman", 3.95, publisher);
        datastore.save(book);
        List<Book> books = datastore.createQuery(Book.class)
            .field("title")
            .contains("Learning Java")
            .project("title", true)
            .find()
            .toList();
        assertEquals(books.size(), 1);
        assertEquals("Learning Java", books.get(0)
            .getTitle());
        assertNull(books.get(0)
            .getAuthor());
    }

}

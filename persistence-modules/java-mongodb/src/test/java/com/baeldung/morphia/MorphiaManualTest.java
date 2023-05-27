package com.baeldung.morphia;

import static dev.morphia.aggregation.Group.grouping;
import static dev.morphia.aggregation.Group.push;
import static dev.morphia.query.experimental.filters.Filters.eq;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.bson.types.ObjectId;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.morphia.domain.Author;
import com.baeldung.morphia.domain.Book;
import com.baeldung.morphia.domain.Publisher;
import com.mongodb.client.MongoClients;
import com.mongodb.client.model.ReturnDocument;

import dev.morphia.Datastore;
import dev.morphia.DeleteOptions;
import dev.morphia.ModifyOptions;
import dev.morphia.Morphia;
import dev.morphia.query.FindOptions;
import dev.morphia.query.Query;
import dev.morphia.query.experimental.updates.UpdateOperators;

/**
 * 1. Firstly you have to install a docker service where you can run a docker container. For Windows you can use Docker desktop (where you can have pretty
 * much the seme functionality as on linux)
 * 2. Secondly run a mongodb instance: with this command: docker run -d --rm -p 27017:27017 --name mongo2 mongo:5
 * 3. Thirdly run this test
 */
public class MorphiaManualTest {

    private static Datastore datastore;
    private static ObjectId id = new ObjectId();

   @BeforeClass
    public static void setUp() {
        datastore = Morphia.createDatastore(MongoClients.create(), "library");
        datastore.getMapper().mapPackage("com.baeldung.morphia");
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

        Query<Book> booksQuery = datastore.find(Book.class)
                .filter(eq("title", "Learning Java"));
        List<Book> books = StreamSupport
                .stream(booksQuery.spliterator(), true)
                .collect(Collectors.toList());
        assertEquals(1, books.size());
        assertEquals(book, books.get(0));
    }

    @Test
    public void givenDocument_whenUpdated_thenUpdateReflected() {
        Publisher publisher = new Publisher(id, "Awsome Publisher");
        Book book = new Book("9781565927186", "Learning Java", "Tom Kirkman", 3.95, publisher);
        datastore.save(book);

        final Book execute = datastore.find(Book.class)
                .filter(eq("title", "Learning Java"))
                .modify(UpdateOperators.set("price", 4.95))
                .execute(new ModifyOptions().returnDocument(ReturnDocument.AFTER));

        assertEquals(4.95, execute.getCost());
    }

    @Test
    public void givenDocument_whenDeleted_thenDeleteReflected() {
        Publisher publisher = new Publisher(id, "Awsome Publisher");
        Book book = new Book("9781565927186", "Learning Java", "Tom Kirkman", 3.95, publisher);
        datastore.save(book);
        datastore.find(Book.class)
                .filter(eq("title", "Learning Java"))
                .delete(new DeleteOptions().multi(true));
        Query<Book> books = datastore.find(Book.class)
                .filter(eq("title", "Learning Java"));
        assertFalse(books.iterator().hasNext());
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
        List<Book> books = datastore.find(Book.class)
                .filter(eq("title", "Learning Java"))
                .iterator(new FindOptions().projection().include("title")).toList();
        assertEquals( 1, books.size());
        assertEquals("Learning Java", books.get(0).getTitle());
        assertNull(books.get(0).getAuthor());
    }

}

package com.baeldung.cassandra.java.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.baeldung.cassandra.java.client.domain.Book;
import com.baeldung.cassandra.java.client.repository.BookRepository;
import com.baeldung.cassandra.java.client.repository.KeyspaceRepository;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.utils.UUIDs;

public class CassandraClient {
    private static final Logger LOG = LoggerFactory.getLogger(CassandraClient.class);

    public static void main(String args[]) {
        CassandraConnector connector = new CassandraConnector();
        connector.connect("127.0.0.1", null);
        Session session = connector.getSession();

        KeyspaceRepository sr = new KeyspaceRepository(session);
        sr.createKeyspace("library", "SimpleStrategy", 1);
        sr.useKeyspace("library");

        BookRepository br = new BookRepository(session);
        br.createTable();
        br.alterTablebooks("publisher", "text");

        br.createTableBooksByTitle();

        Book book = new Book(UUIDs.timeBased(), "Effective Java", "Joshua Bloch", "Programming");
        br.insertBookBatch(book);

        br.selectAll().forEach(o -> LOG.info("Title in books: " + o.getTitle()));
        br.selectAllBookByTitle().forEach(o -> LOG.info("Title in booksByTitle: " + o.getTitle()));

        br.deletebookByTitle("Effective Java");
        br.deleteTable("books");
        br.deleteTable("booksByTitle");

        sr.deleteKeyspace("library");

        connector.close();
    }
}

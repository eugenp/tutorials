package com.baeldung.jooq.jointables;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.jooq.jointables.public_.Tables;
import com.baeldung.jooq.jointables.public_.tables.Book;
import com.baeldung.jooq.jointables.public_.tables.Bookauthor;
import com.baeldung.jooq.jointables.public_.tables.Store;

public class JoinTablesLiveTest {

    static DSLContext context;

    @BeforeClass
    public static void setUp() throws Exception {
        // URL jooqConfigURL = getClass().getClassLoader().getResource("jooq-config-2.xml");
        // File file = new File(jooqConfigURL.getFile());
        // GenerationTool.generate(Files.readString(file.toPath()));

        String url = "jdbc:postgresql://localhost:5432/postgres";
        String username = "postgres";
        String password = "";

        Connection conn = DriverManager.getConnection(url, username, password);
        context = DSL.using(conn, SQLDialect.POSTGRES);

        context.insertInto(Tables.STORE, Store.STORE.ID, Store.STORE.NAME)
            .values(1, "ABC Branch I ")
            .values(2, "ABC Branch II")
            .execute();

        context.insertInto(Tables.BOOK, Book.BOOK.ID, Book.BOOK.TITLE, Book.BOOK.DESCRIPTION, Book.BOOK.AUTHOR_ID, Book.BOOK.STORE_ID)
            .values(1, "Book 1", "This is book 1", 1, 1)
            .values(2, "Book 2", "This is book 2", 2, 2)
            .values(3, "Book 3", "This is book 3", 1, 2)
            .values(4, "Book 4", "This is book 4", 5, 1)
            .execute();

        context.insertInto(Tables.BOOKAUTHOR, Bookauthor.BOOKAUTHOR.ID, Bookauthor.BOOKAUTHOR.NAME, Bookauthor.BOOKAUTHOR.COUNTRY)
            .values(1, "John Smith", "Japan")
            .values(2, "William Walce", "Japan")
            .values(3, "Marry Sity", "South Korea")
            .values(4, "Morry Toh", "England")
            .execute();
    }

    @AfterClass
    public static void cleanup() throws Exception {
        context.truncateTable(Store.STORE)
            .execute();
        context.truncateTable(Book.BOOK)
            .execute();
        context.truncateTable(Bookauthor.BOOKAUTHOR)
            .execute();
    }

    @Test
    public void _whenUsingJoinMethod_thenQueryExecuted() {
        Result<Record> result = JoinTables.usingJoinMethod(context);
        assertEquals(3, result.size());
    }

    @Test
    public void _whenUsingMultipleJoinMethod_thenQueryExecuted() {
        Result<Record> result = JoinTables.usingMultipleJoinMethod(context);
        assertEquals(3, result.size());
    }

    @Test
    public void givenContext_whenUsingLeftOuterJoinMethod_thenQueryExecuted() {
        Result<Record> result = JoinTables.usingLeftOuterJoinMethod(context);
        assertEquals(4, result.size());
    }

    @Test
    public void whenUsingRightOuterJoinMethod_thenQueryExecuted() {
        Result<Record> result = JoinTables.usingRightOuterJoinMethod(context);
        assertEquals(5, result.size());
    }

    @Test
    public void whenUsingFullOuterJoinMethod_thenQueryExecuted() {
        Result<Record> result = JoinTables.usingFullOuterJoinMethod(context);
        assertEquals(6, result.size());
    }

    @Test
    public void whenUsingNaturalJoinMethod_thenQueryExecuted() {
        Result<Record> result = JoinTables.usingNaturalJoinMethod(context);
        assertEquals(4, result.size());
    }

    @Test
    public void whenUsingCrossJoinMethod_thenQueryExecuted() {
        Result<Record> result = JoinTables.usingCrossJoinMethod(context);
        assertEquals(8, result.size());
    }
}

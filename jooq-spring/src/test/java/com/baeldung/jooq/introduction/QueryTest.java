package com.baeldung.jooq.introduction;

import com.baeldung.jooq.introduction.db.public_.tables.Author;
import com.baeldung.jooq.introduction.db.public_.tables.AuthorBook;
import com.baeldung.jooq.introduction.db.public_.tables.Book;
import org.jooq.DSLContext;
import org.jooq.Record3;
import org.jooq.Result;
import org.jooq.impl.DSL;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertEquals;

@ContextConfiguration(classes = PersistenceContext.class)
@Transactional(transactionManager = "transactionManager")
@RunWith(SpringJUnit4ClassRunner.class)
public class QueryTest {

    @Autowired
    private DSLContext dsl;

    Author author = Author.AUTHOR;
    Book book = Book.BOOK;
    AuthorBook authorBook = AuthorBook.AUTHOR_BOOK;

    @Test
    public void givenValidData_whenInserting_thenSucceed() {
        dsl.insertInto(author).set(author.ID, 4).set(author.FIRST_NAME, "Herbert").set(author.LAST_NAME, "Schildt").execute();
        dsl.insertInto(book).set(book.ID, 4).set(book.TITLE, "A Beginner's Guide").execute();
        dsl.insertInto(authorBook).set(authorBook.AUTHOR_ID, 4).set(authorBook.BOOK_ID, 4).execute();
        Result<Record3<Integer, String, Integer>> result = dsl.select(author.ID, author.LAST_NAME, DSL.count()).from(author).join(authorBook).on(author.ID.equal(authorBook.AUTHOR_ID)).join(book).on(authorBook.BOOK_ID.equal(book.ID))
                .groupBy(author.LAST_NAME).fetch();

        assertEquals(3, result.size());
        assertEquals("Sierra", result.getValue(0, author.LAST_NAME));
        assertEquals(Integer.valueOf(2), result.getValue(0, DSL.count()));
        assertEquals("Schildt", result.getValue(2, author.LAST_NAME));
        assertEquals(Integer.valueOf(1), result.getValue(2, DSL.count()));
    }

    @Test(expected = DataAccessException.class)
    public void givenInvalidData_whenInserting_thenFail() {
        dsl.insertInto(authorBook).set(authorBook.AUTHOR_ID, 4).set(authorBook.BOOK_ID, 5).execute();
    }

    @Test
    public void givenValidData_whenUpdating_thenSucceed() {
        dsl.update(author).set(author.LAST_NAME, "Baeldung").where(author.ID.equal(3)).execute();
        dsl.update(book).set(book.TITLE, "Building your REST API with Spring").where(book.ID.equal(3)).execute();
        dsl.insertInto(authorBook).set(authorBook.AUTHOR_ID, 3).set(authorBook.BOOK_ID, 3).execute();
        Result<Record3<Integer, String, String>> result = dsl.select(author.ID, author.LAST_NAME, book.TITLE).from(author).join(authorBook).on(author.ID.equal(authorBook.AUTHOR_ID)).join(book).on(authorBook.BOOK_ID.equal(book.ID)).where(author.ID.equal(3))
                .fetch();

        assertEquals(1, result.size());
        assertEquals(Integer.valueOf(3), result.getValue(0, author.ID));
        assertEquals("Baeldung", result.getValue(0, author.LAST_NAME));
        assertEquals("Building your REST API with Spring", result.getValue(0, book.TITLE));
    }

    @Test(expected = DataAccessException.class)
    public void givenInvalidData_whenUpdating_thenFail() {
        dsl.update(authorBook).set(authorBook.AUTHOR_ID, 4).set(authorBook.BOOK_ID, 5).execute();
    }

    @Test
    public void givenValidData_whenDeleting_thenSucceed() {
        dsl.delete(author).where(author.ID.lt(3)).execute();
        Result<Record3<Integer, String, String>> result = dsl.select(author.ID, author.FIRST_NAME, author.LAST_NAME).from(author).fetch();

        assertEquals(1, result.size());
        assertEquals("Bryan", result.getValue(0, author.FIRST_NAME));
        assertEquals("Basham", result.getValue(0, author.LAST_NAME));
    }

    @Test(expected = DataAccessException.class)
    public void givenInvalidData_whenDeleting_thenFail() {
        dsl.delete(book).where(book.ID.equal(1)).execute();
    }
}
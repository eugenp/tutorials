package com.baeldung.hibernate.hibernateexception;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.ArrayList;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

class HibernateExceptionUnitTest {

    @Test
    void whenUsingOneHibernateSession_thenCorrect() {
        try (Session session1 = HibernateUtil.getSessionFactory()
            .openSession()) {
            session1.beginTransaction();

            Author author = new Author();
            author.setId(2L);
            author.setName("Jane Austen");
            author.setBooks(new ArrayList<Book>());
            session1.persist(author);

            Author persistedAuthor = session1.get(Author.class, 2L);

            Book newBook = new Book();
            newBook.setId(1L);
            newBook.setTitle("Pride and Prejudice");
            persistedAuthor.getBooks()
                .add(newBook);
            session1.update(persistedAuthor);

            session1.getTransaction()
                .commit();
        }
    }

    @Test
    void whenUsingMoreThanOneHibernateSessionWithMergeMethod_thenCorrect() {
        try (Session session1 = HibernateUtil.getSessionFactory()
            .openSession();
            Session session2 = HibernateUtil.getSessionFactory()
                .openSession()) {
            session1.beginTransaction();

            Author author = new Author();
            author.setId(3L);
            author.setName("Leo Tolstoy");
            author.setBooks(new ArrayList<Book>());
            session1.persist(author);
            Author persistedAuthor = session1.get(Author.class, 3L);
            session1.getTransaction()
                .commit();

            session2.beginTransaction();
            Book newBook = new Book();
            newBook.setId(1L);
            newBook.setTitle("War and Peace");
            persistedAuthor.getBooks()
                .add(newBook);
            session2.merge(persistedAuthor);
            session2.getTransaction()
                .commit();
        }
    }

    @Test
    void whenUsingMoreThanOneHibernateSession_thenThrowHibernateException() {
        assertThatThrownBy(() -> {
            try (Session session1 = HibernateUtil.getSessionFactory()
                .openSession();
                Session session2 = HibernateUtil.getSessionFactory()
                    .openSession()) {
                session1.beginTransaction();

                Author author = new Author();
                author.setId(1L);
                author.setName("Leo Tolstoy");
                author.setBooks(new ArrayList<Book>());
                session1.persist(author);
                Author persistedAuthor = session1.get(Author.class, 1L);
                session1.getTransaction()
                    .commit();

                session2.beginTransaction();
                Book newBook = new Book();
                newBook.setId(1L);
                newBook.setTitle("War and Peace");
                persistedAuthor.getBooks()
                    .add(newBook);
                session2.update(persistedAuthor);
                session2.getTransaction()
                    .commit();
            }
        }).isInstanceOf(HibernateException.class)
            .hasMessageContaining("Illegal attempt to associate a collection with two open sessions");
    }

}

package com.baeldung.hibernate.hibernateexception;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.junit.jupiter.api.Test;

class HibernateExceptionUnitTest {

    @Test
    void givenAnEntity_whenChangesUseTheSameHibernateSession_thenThoseChangesCanBeUpdated() {
        try (Session session1 = HibernateUtil.getSessionFactory()
            .openSession()) {
            session1.beginTransaction();

            Author author = new Author("Jane Austen");
            session1.persist(author);

            Book newBook = new Book("Pride and Prejudice");
            author.getBooks()
                .add(newBook);

            session1.update(author);

            session1.getTransaction()
                .commit();
        }
    }

    @Test
    void givenAnEntity_whenChangesSpanMultipleHibernateSessions_thenThoseChangesCanBeMerged() {
        try (Session session1 = HibernateUtil.getSessionFactory()
            .openSession();
            Session session2 = HibernateUtil.getSessionFactory()
                .openSession()) {
            session1.beginTransaction();

            Author author = new Author("Leo Tolstoy");
            session1.persist(author);
            session1.getTransaction()
                .commit();

            session2.beginTransaction();

            Book newBook = new Book("War and Peace");
            author.getBooks()
                .add(newBook);
            session2.merge(author);

            session2.getTransaction()
                .commit();
        }
    }

    @Test
    void givenAnEntity_whenChangesSpanMultipleHibernateSessions_thenThoseChangesCanNotBeUpdated() {
        assertThatThrownBy(() -> {
            try (Session session1 = HibernateUtil.getSessionFactory()
                .openSession();
                Session session2 = HibernateUtil.getSessionFactory()
                    .openSession()) {
                session1.beginTransaction();

                Author author = new Author("Leo Tolstoy");
                session1.persist(author);

                session1.getTransaction()
                    .commit();

                session2.beginTransaction();

                Book newBook = new Book("War and Peace");
                author.getBooks()
                    .add(newBook);
                session2.update(author);

                session2.getTransaction()
                    .commit();
            }
        }).isInstanceOf(HibernateException.class)
            .hasMessageContaining("Illegal attempt to associate a collection with two open sessions");
    }

}

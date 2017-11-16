package com.baeldung.hibernate;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.hibernate.pojo.inheritance.Book;
import com.baeldung.hibernate.pojo.inheritance.Pen;
import com.baeldung.hibernate.pojo.inheritance.MyProduct;

public class InheritanceMappingIntegrationTest {
    private Session session;

    private Transaction transaction;

    @Before
    public void setUp() throws IOException {
        session = HibernateUtil.getSessionFactory()
            .openSession();
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() {
        transaction.rollback();
        session.close();
    }

    @Test
    public void givenSubclasses_whenQuerySuperclass_thenOk() {
        Book book = new Book(1, "1984", "George Orwell");
        session.save(book);
        Pen pen = new Pen(2, "my pen", "blue");
        session.save(pen);

        assertThat(session.createQuery("from MyProduct")
            .getResultList()
            .size()).isEqualTo(2);

    }
}

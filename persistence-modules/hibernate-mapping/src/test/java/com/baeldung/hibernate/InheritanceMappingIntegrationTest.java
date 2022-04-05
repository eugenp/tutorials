package com.baeldung.hibernate;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.hibernate.pojo.inheritance.Bag;
import com.baeldung.hibernate.pojo.inheritance.Book;
import com.baeldung.hibernate.pojo.inheritance.Car;
import com.baeldung.hibernate.pojo.inheritance.MyEmployee;
import com.baeldung.hibernate.pojo.inheritance.Pen;
import com.baeldung.hibernate.pojo.inheritance.Pet;

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
    public void givenSubclasses_whenQuerySingleTableSuperclass_thenOk() {
        Book book = new Book(1, "1984", "George Orwell");
        session.save(book);
        Pen pen = new Pen(2, "my pen", "blue");
        session.save(pen);

        assertThat(session.createQuery("from MyProduct")
            .getResultList()
            .size()).isEqualTo(2);
    }

    @Test
    public void givenSubclasses_whenQueryMappedSuperclass_thenOk() {
        MyEmployee emp = new MyEmployee(1, "john", "baeldung");
        session.save(emp);

        assertThat(session.createQuery("from com.baeldung.hibernate.pojo.inheritance.Person")
            .getResultList()
            .size()).isEqualTo(1);
    }

    @Test
    public void givenSubclasses_whenQueryJoinedTableSuperclass_thenOk() {
        Pet pet = new Pet(1, "dog", "lassie");
        session.save(pet);

        assertThat(session.createQuery("from Animal")
            .getResultList()
            .size()).isEqualTo(1);
    }

    @Test
    public void givenSubclasses_whenQueryTablePerClassSuperclass_thenOk() {
        Car car = new Car(1, "audi", "xyz");
        session.save(car);

        assertThat(session.createQuery("from Vehicle")
            .getResultList()
            .size()).isEqualTo(1);
    }

    @Test
    public void givenSubclasses_whenQueryNonMappedInterface_thenOk() {
        Bag bag = new Bag(1, "large");
        session.save(bag);

        assertThat(session.createQuery("from com.baeldung.hibernate.pojo.inheritance.Item")
            .getResultList()
            .size()).isEqualTo(0);
    }
}

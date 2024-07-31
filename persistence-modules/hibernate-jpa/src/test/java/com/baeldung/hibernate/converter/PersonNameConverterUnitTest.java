package com.baeldung.hibernate.converter;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.pojo.Person;
import com.baeldung.hibernate.pojo.PersonName;

import static org.junit.Assert.assertEquals;

public class PersonNameConverterUnitTest {

    private Session session;
    private Transaction transaction;

    @Before
    public void setUp() throws IOException {
        session = HibernateUtil.getSessionFactory()
            .openSession();
        transaction = session.beginTransaction();

        session.createNativeQuery("delete from personTable")
            .executeUpdate();

        transaction.commit();
        transaction = session.beginTransaction();
    }

    @After
    public void tearDown() {
        transaction.rollback();
        session.close();
    }

    @Test
    public void givenPersonName_WhenSaving_ThenNameAndSurnameConcat() {
        final String name = "name";
        final String surname = "surname";

        PersonName personName = new PersonName();
        personName.setName(name);
        personName.setSurname(surname);

        Person person = new Person();
        person.setPersonName(personName);

        Long id = (Long) session.save(person);

        session.flush();
        session.clear();

        String dbPersonName = (String) session.createNativeQuery("select p.personName from PersonTable p where p.id = :id")
            .setParameter("id", id)
            .getSingleResult();

        assertEquals(surname + ", " + name, dbPersonName);

        Person dbPerson = session.createNativeQuery("select * from PersonTable p where p.id = :id", Person.class)
            .setParameter("id", id)
            .getSingleResult();

        assertEquals(dbPerson.getPersonName()
            .getName(), name);
        assertEquals(dbPerson.getPersonName()
            .getSurname(), surname);
    }

    @Test
    public void givenPersonNameNull_WhenSaving_ThenNullStored() {
        final String name = null;
        final String surname = null;

        PersonName personName = new PersonName();
        personName.setName(name);
        personName.setSurname(surname);

        Person person = new Person();
        person.setPersonName(personName);

        Long id = (Long) session.save(person);

        session.flush();
        session.clear();

        String dbPersonName = (String) session.createNativeQuery("select p.personName from PersonTable p where p.id = :id")
            .setParameter("id", id)
            .getSingleResult();

        assertEquals("", dbPersonName);

        Person dbPerson = session.createNativeQuery("select * from PersonTable p where p.id = :id", Person.class)
            .setParameter("id", id)
            .getSingleResult();

        assertEquals(dbPerson.getPersonName(), null);
    }

    @Test
    public void givenPersonNameWithoutName_WhenSaving_ThenNotNameStored() {
        final String name = null;
        final String surname = "surname";

        PersonName personName = new PersonName();
        personName.setName(name);
        personName.setSurname(surname);

        Person person = new Person();
        person.setPersonName(personName);

        Long id = (Long) session.save(person);

        session.flush();
        session.clear();

        String dbPersonName = (String) session.createNativeQuery("select p.personName from PersonTable p where p.id = :id")
            .setParameter("id", id)
            .getSingleResult();

        assertEquals("surname, ", dbPersonName);

        Person dbPerson = session.createNativeQuery("select * from PersonTable p where p.id = :id", Person.class)
            .setParameter("id", id)
            .getSingleResult();

        assertEquals(dbPerson.getPersonName()
            .getName(), name);
        assertEquals(dbPerson.getPersonName()
            .getSurname(), surname);
    }

    @Test
    public void givenPersonNameWithoutSurName_WhenSaving_ThenNotSurNameStored() {
        final String name = "name";
        final String surname = null;

        PersonName personName = new PersonName();
        personName.setName(name);
        personName.setSurname(surname);

        Person person = new Person();
        person.setPersonName(personName);

        Long id = (Long) session.save(person);

        session.flush();
        session.clear();

        String dbPersonName = (String) session.createNativeQuery("select p.personName from PersonTable p where p.id = :id")
            .setParameter("id", id)
            .getSingleResult();

        assertEquals("name", dbPersonName);

        Person dbPerson = session.createNativeQuery("select * from PersonTable p where p.id = :id", Person.class)
            .setParameter("id", id)
            .getSingleResult();

        assertEquals(dbPerson.getPersonName()
            .getName(), name);
        assertEquals(dbPerson.getPersonName()
            .getSurname(), surname);
    }

    @Test
    public void givenPersonNameEmptyFields_WhenSaving_ThenFielsNotStored() {
        final String name = "";
        final String surname = "";

        PersonName personName = new PersonName();
        personName.setName(name);
        personName.setSurname(surname);

        Person person = new Person();
        person.setPersonName(personName);

        Long id = (Long) session.save(person);

        session.flush();
        session.clear();

        String dbPersonName = (String) session.createNativeQuery("select p.personName from PersonTable p where p.id = :id")
            .setParameter("id", id)
            .getSingleResult();

        assertEquals("", dbPersonName);

        Person dbPerson = session.createNativeQuery("select * from PersonTable p where p.id = :id", Person.class)
            .setParameter("id", id)
            .getSingleResult();

        assertEquals(dbPerson.getPersonName(), null);
    }

}

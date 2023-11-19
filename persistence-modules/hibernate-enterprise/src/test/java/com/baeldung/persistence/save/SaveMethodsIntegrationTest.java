package com.baeldung.persistence.save;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import jakarta.persistence.PersistenceException;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.HSQLDialect;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.baeldung.persistence.model.Person;

/**
 * Testing specific implementation details for different methods:
 * persist, save, merge, update, saveOrUpdate.
 */
public class SaveMethodsIntegrationTest {

    private static SessionFactory sessionFactory;

    private Session session;
    private boolean doNotCommit = false;

    @BeforeClass
    public static void beforeTests() {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class)
            .setProperty("hibernate.dialect", HSQLDialect.class.getName())
            .setProperty("hibernate.connection.driver_class", org.hsqldb.jdbcDriver.class.getName())
            .setProperty("hibernate.connection.url", "jdbc:hsqldb:mem:test")
            .setProperty("hibernate.connection.username", "sa")
            .setProperty("hibernate.connection.password", "")
            .setProperty("hibernate.hbm2ddl.auto", "update");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
            .build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();
        doNotCommit = false;
    }

    @Test
    public void whenPersistTransient_thenSavedToDatabaseOnCommit() {

        Person person = new Person();
        person.setName("John");
        session.persist(person);

        session.getTransaction()
            .commit();
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();

        assertNotNull(session.get(Person.class, person.getId()));

    }

    @Test
    public void whenPersistPersistent_thenNothingHappens() {

        Person person = new Person();
        person.setName("John");

        session.persist(person);
        Long id1 = person.getId();

        session.persist(person);
        Long id2 = person.getId();

        assertEquals(id1, id2);
    }

    @Test(expected = PersistenceException.class)
    public void whenPersistDetached_thenThrowsException() {

        doNotCommit = true;
        
        Person person = new Person();
        person.setName("John");
        session.persist(person);
        session.evict(person);
        
        session.persist(person);
    }

    @Test
    public void whenMergeDetached_thenEntityUpdatedFromDatabase() {

        Person person = new Person();
        person.setName("John");
        session.save(person);
        session.flush();
        session.evict(person);

        person.setName("Mary");
        Person mergedPerson = (Person) session.merge(person);

        assertNotSame(person, mergedPerson);
        assertEquals("Mary", mergedPerson.getName());

    }

    @Test
    public void whenSaveTransient_thenIdGeneratedImmediately() {

        Person person = new Person();
        person.setName("John");

        assertNull(person.getId());

        Long id = (Long) session.save(person);

        assertNotNull(id);

        session.getTransaction()
            .commit();
        session.close();

        assertEquals(id, person.getId());

        session = sessionFactory.openSession();
        session.beginTransaction();

        assertNotNull(session.get(Person.class, person.getId()));

    }

    @Test
    public void whenSavePersistent_thenNothingHappens() {

        Person person = new Person();
        person.setName("John");
        Long id1 = (Long) session.save(person);
        Long id2 = (Long) session.save(person);
        assertEquals(id1, id2);

    }

    @Test
    public void whenSaveDetached_thenNewInstancePersisted() {

        Person person = new Person();
        person.setName("John");
        Long id1 = (Long) session.save(person);
        session.evict(person);

        Long id2 = (Long) session.save(person);
        assertNotEquals(id1, id2);

    }

    @Test
    public void whenMergeTransient_thenNewEntitySavedToDatabase() {

        Person person = new Person();
        person.setName("John");
        Person mergedPerson = (Person) session.merge(person);

        session.getTransaction()
            .commit();
        session.beginTransaction();

        assertNull(person.getId());
        assertNotNull(mergedPerson.getId());

    }

    @Test
    public void whenMergePersistent_thenReturnsSameObject() {

        Person person = new Person();
        person.setName("John");
        session.save(person);

        Person mergedPerson = (Person) session.merge(person);

        assertSame(person, mergedPerson);

    }

    @Test
    public void whenUpdateDetached_thenEntityUpdatedFromDatabase() {

        Person person = new Person();
        person.setName("John");
        session.save(person);
        session.evict(person);

        person.setName("Mary");
        session.update(person);
        assertEquals("Mary", person.getName());

    }

    @Test(expected = HibernateException.class)
    public void whenUpdateTransient_thenThrowsException() {

        Person person = new Person();
        person.setName("John");
        session.update(person);

    }

    @Test
    public void whenUpdatePersistent_thenNothingHappens() {

        Person person = new Person();
        person.setName("John");
        session.save(person);

        session.update(person);

    }

    @Test
    public void whenSaveOrUpdateDetached_thenEntityUpdatedFromDatabase() {

        Person person = new Person();
        person.setName("John");
        session.save(person);
        session.evict(person);

        person.setName("Mary");
        session.saveOrUpdate(person);
        assertEquals("Mary", person.getName());

    }

    @Test
    public void whenSaveOrUpdateTransient_thenSavedToDatabaseOnCommit() {

        Person person = new Person();
        person.setName("John");
        session.saveOrUpdate(person);

        session.getTransaction()
            .commit();
        session.close();

        session = sessionFactory.openSession();
        session.beginTransaction();

        assertNotNull(session.get(Person.class, person.getId()));

    }

    @Test
    public void whenSaveOrUpdatePersistent_thenNothingHappens() {

        Person person = new Person();
        person.setName("John");
        session.save(person);

        session.saveOrUpdate(person);

    }

    @After
    public void tearDown() {
        if (!doNotCommit) {
            session.getTransaction()
                .commit();
        }
        session.close();
    }

    @AfterClass
    public static void afterTests() {
        sessionFactory.close();
    }

}

package com.baeldung.persistence.save;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;

import jakarta.persistence.PersistenceException;

import org.h2.tools.RunScript;
import org.hibernate.HibernateException;
import org.hibernate.ReplicationMode;
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

import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Testing specific implementation details for different methods:
 * persist, save, merge, update, saveOrUpdate, refresh, replicate
 */
public class SaveMethodsIntegrationTest {

    private static SessionFactory sessionFactory1;

    private static SessionFactory sessionFactory2;

    private Session session1;

    private Session session2;
    private boolean doNotCommit = false;

    private static SessionFactory createSessionFactoryAndInitializeDBs(String dbUrl) throws Exception {
        Configuration configuration = new Configuration().addAnnotatedClass(Person.class)
          .setProperty("hibernate.dialect", HSQLDialect.class.getName())
          .setProperty("hibernate.connection.driver_class", org.hsqldb.jdbcDriver.class.getName())
          .setProperty("hibernate.connection.url", dbUrl)
          .setProperty("hibernate.connection.username", "sa")
          .setProperty("hibernate.connection.password", "")
          .setProperty("hibernate.hbm2ddl.auto", "update");
        Connection connection = DriverManager.getConnection(dbUrl, "sa", "");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties())
                .build();
        SessionFactory sf = configuration.buildSessionFactory(serviceRegistry);
        try (InputStream h2InitStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("h2-trigger.sql")) {
            assert h2InitStream != null;
            try (InputStreamReader h2InitReader = new InputStreamReader(h2InitStream)) {
                RunScript.execute(connection, h2InitReader);
            }
        }
        return sf;
    }
    @BeforeClass
    public static void beforeTests() throws Exception{
        sessionFactory1 = createSessionFactoryAndInitializeDBs("jdbc:hsqldb:mem:test");
        sessionFactory2 = createSessionFactoryAndInitializeDBs("jdbc:hsqldb:mem:test2");

    }

    @Before
    public void setUp() throws Exception {
        session1 = sessionFactory1.openSession();
        session1.beginTransaction();

        doNotCommit = false;
    }

    @Test
    public void whenPersistTransient_thenSavedToDatabaseOnCommit() {

        Person person = new Person();
        person.setName("John");
        session1.persist(person);

        session1.getTransaction()
                .commit();
        session1.close();

        session1 = sessionFactory1.openSession();
        session1.beginTransaction();

        assertNotNull(session1.get(Person.class, person.getId()));

    }

    @Test
    public void whenPersistPersistent_thenNothingHappens() {

        Person person = new Person();
        person.setName("John");

        session1.persist(person);
        Long id1 = person.getId();

        session1.persist(person);
        Long id2 = person.getId();

        assertEquals(id1, id2);
    }

    @Test(expected = PersistenceException.class)
    public void whenPersistDetached_thenThrowsException() {

        doNotCommit = true;

        Person person = new Person();
        person.setName("John");
        session1.persist(person);
        session1.evict(person);

        session1.persist(person);
    }

    @Test
    public void whenMergeDetached_thenEntityUpdatedFromDatabase() {

        Person person = new Person();
        person.setName("John");
        session1.save(person);
        session1.flush();
        session1.evict(person);

        person.setName("Mary");
        Person mergedPerson = (Person) session1.merge(person);

        assertNotSame(person, mergedPerson);
        assertEquals("Mary", mergedPerson.getName());

    }

    @Test
    public void whenSaveTransient_thenIdGeneratedImmediately() {

        Person person = new Person();
        person.setName("John");

        assertNull(person.getId());

        Long id = (Long) session1.save(person);

        assertNotNull(id);

        session1.getTransaction()
                .commit();
        session1.close();

        assertEquals(id, person.getId());

        session1 = sessionFactory1.openSession();
        session1.beginTransaction();

        assertNotNull(session1.get(Person.class, person.getId()));

    }

    @Test
    public void whenSavePersistent_thenNothingHappens() {

        Person person = new Person();
        person.setName("John");
        Long id1 = (Long) session1.save(person);
        Long id2 = (Long) session1.save(person);
        assertEquals(id1, id2);

    }

    @Test
    public void whenSaveDetached_thenNewInstancePersisted() {

        Person person = new Person();
        person.setName("John");
        Long id1 = (Long) session1.save(person);
        session1.evict(person);

        Long id2 = (Long) session1.save(person);
        assertNotEquals(id1, id2);

    }

    @Test
    public void whenMergeTransient_thenNewEntitySavedToDatabase() {

        Person person = new Person();
        person.setName("John");
        Person mergedPerson = (Person) session1.merge(person);

        session1.getTransaction()
                .commit();
        session1.beginTransaction();

        assertNotNull(person.getId());
        assertNotNull(mergedPerson.getId());

    }

    @Test
    public void whenMergePersistent_thenReturnsSameObject() {

        Person person = new Person();
        person.setName("John");
        session1.save(person);

        Person mergedPerson = (Person) session1.merge(person);

        assertSame(person, mergedPerson);

    }

    @Test
    public void whenUpdateDetached_thenEntityUpdatedFromDatabase() {

        Person person = new Person();
        person.setName("John");
        session1.save(person);
        session1.evict(person);

        person.setName("Mary");
        session1.update(person);
        assertEquals("Mary", person.getName());

    }

    @Test(expected = HibernateException.class)
    public void whenUpdateTransient_thenThrowsException() {

        Person person = new Person();
        person.setName("John");
        session1.update(person);

    }

    @Test
    public void whenUpdatePersistent_thenNothingHappens() {

        Person person = new Person();
        person.setName("John");
        session1.save(person);

        session1.update(person);

    }

    @Test
    public void whenSaveOrUpdateDetached_thenEntityUpdatedFromDatabase() {

        Person person = new Person();
        person.setName("John");
        session1.save(person);
        session1.evict(person);

        person.setName("Mary");
        session1.saveOrUpdate(person);
        assertEquals("Mary", person.getName());

    }

    @Test
    public void whenSaveOrUpdateTransient_thenSavedToDatabaseOnCommit() {

        Person person = new Person();
        person.setName("John");
        session1.saveOrUpdate(person);

        session1.getTransaction()
                .commit();
        session1.close();

        session1 = sessionFactory1.openSession();
        session1.beginTransaction();

        assertNotNull(session1.get(Person.class, person.getId()));

    }

    @Test
    public void whenSaveAndTriggerUpdatedAndRefresh_thenRefreshPersistentEntity() {

        Person person = new Person();
        person.setName("Zeeshan Arif");
        session1.persist(person);
        session1.getTransaction()
                .commit();
        session1.close();

        session1 = sessionFactory1.openSession();
        session1.beginTransaction();
        session1.refresh(person);
        session1.getTransaction()
                .commit();
        session1.close();

        session1 = sessionFactory1.openSession();
        session1.beginTransaction();
        assertEquals(person.getName(), "Neymar Santos");

    }

    @Test
    public void whenReplicate_thenRefreshPersistentEntity() {

        Person p = new Person();
        p.setName("Ronaldinho Gaucho");
        session1.persist(p);
        session1.getTransaction()
                .commit();
        session1.close();

        Session session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        session2.replicate(p, ReplicationMode.LATEST_VERSION);
        session2.getTransaction()
                .commit();
        session2.close();

        session2 = sessionFactory2.openSession();
        session2.beginTransaction();
        Person actual = session2.get(Person.class, p.getId());
        session2.getTransaction().commit();
        session2.close();

        session1 = sessionFactory1.openSession();
        session1.beginTransaction();
        Person expected = session1.get(Person.class, p.getId());
        session1.getTransaction().commit();
        session1.close();

        session1 = sessionFactory1.openSession();
        session1.beginTransaction();
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getName(), actual.getName());

    }

    @Test
    public void whenSaveOrUpdatePersistent_thenNothingHappens() {

        Person person = new Person();
        person.setName("John");
        session1.save(person);

        session1.saveOrUpdate(person);

    }

    @After
    public void tearDown() {
        if (!doNotCommit) {
            session1.getTransaction()
                    .commit();
        }
        session1.close();
    }

    @AfterClass
    public static void afterTests() {
        sessionFactory1.close();
        sessionFactory2.close();
    }

}

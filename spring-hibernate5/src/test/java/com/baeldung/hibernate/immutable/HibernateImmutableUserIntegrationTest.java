package com.baeldung.hibernate.immutable;

import com.baeldung.hibernate.immutable.entities.User;
import com.baeldung.hibernate.immutable.util.HibernateUtil;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class HibernateImmutableUserIntegrationTest {

    private static Session session;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        createUser();
        session.setCacheMode(CacheMode.REFRESH);
    }

    @BeforeClass
    public static void setup() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
    }

    @AfterClass
    public static void teardown() {
        HibernateUtil.getSessionFactory().close();
    }

    @Test
    public void updateUser() {
        User user = (User) session.createQuery("FROM User WHERE name LIKE '%John%'").list().get(0);
        user.setName("Mike");
        session.update(user);
        session.flush();
        session.refresh(user);

        assertThat(user.getName(), equalTo("John"));
    }

    private static void createUser() {
        User user = new User("John", "Doe");
        session.save(user);
    }
}

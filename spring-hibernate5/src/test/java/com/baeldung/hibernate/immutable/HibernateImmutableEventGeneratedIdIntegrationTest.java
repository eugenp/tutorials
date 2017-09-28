package com.baeldung.hibernate.immutable;

import com.baeldung.hibernate.immutable.entities.EventGeneratedId;
import com.baeldung.hibernate.immutable.util.HibernateUtil;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;

public class HibernateImmutableEventGeneratedIdIntegrationTest {

    private static Session session;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @Before
    public void before() {
        session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        createEventGenerated();
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
    public void updateEventGenerated() {
        EventGeneratedId eventGeneratedId = (EventGeneratedId) session
                .createQuery("FROM EventGeneratedId WHERE name LIKE '%John%'").list().get(0);
        eventGeneratedId.setName("Mike");
        session.update(eventGeneratedId);
        session.flush();
        session.refresh(eventGeneratedId);

        assertThat(eventGeneratedId.getName(), equalTo("John"));
        assertThat(eventGeneratedId.getId(), equalTo(1L));
    }

    private static void createEventGenerated() {
        EventGeneratedId eventGeneratedId = new EventGeneratedId("John", "Doe");
        eventGeneratedId.setId(4L);
        session.save(eventGeneratedId);
    }
}

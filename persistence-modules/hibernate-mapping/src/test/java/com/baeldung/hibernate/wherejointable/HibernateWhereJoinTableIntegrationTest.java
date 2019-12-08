package com.baeldung.hibernate.wherejointable;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.dialect.H2Dialect;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class HibernateWhereJoinTableIntegrationTest {

    private static SessionFactory sessionFactory;

    private Session session;

    /**
     * Test data
     */
    private User user1;
    private User user2;
    private User user3;
    private Group group1;
    private Group group2;

    @BeforeClass
    public static void beforeTests() {
        Configuration configuration = new Configuration().addAnnotatedClass(User.class)
            .addAnnotatedClass(Group.class)
            .addAnnotatedClass(UserGroupRelation.class)
            .setProperty("hibernate.dialect", H2Dialect.class.getName())
            .setProperty("hibernate.connection.driver_class", org.h2.Driver.class.getName())
            .setProperty("hibernate.connection.url", "jdbc:h2:mem:test")
            .setProperty("hibernate.connection.username", "sa")
            .setProperty("hibernate.connection.password", "")
            .setProperty("hibernate.hbm2ddl.auto", "update");
        
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties())
            .build();
        
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    @Before
    public void setUp() {
        session = sessionFactory.openSession();
        session.beginTransaction();

        user1 = new User("user1");
        user2 = new User("user2");
        user3 = new User("user3");

        group1 = new Group("group1");
        group2 = new Group("group2");

        session.save(group1);
        session.save(group2);

        session.save(user1);
        session.save(user2);
        session.save(user3);

        saveRelation(user1, group1, UserGroupRole.MODERATOR);
        saveRelation(user2, group1, UserGroupRole.MODERATOR);
        saveRelation(user3, group1, UserGroupRole.MEMBER);

        saveRelation(user1, group2, UserGroupRole.MEMBER);
        saveRelation(user2, group2, UserGroupRole.MODERATOR);
    }

    @After
    public void tearDown() {
        session.getTransaction().commit();
        session.close();
    }

    @AfterClass
    public static void afterTests() {
        sessionFactory.close();
    }

    @Test
    public void givenUser1_getGroups_returnsAllGroups() {
        List<Group> groups = user1.getGroups();
        assertEquals(2, groups.size());

        assertTrue(groups.contains(group1));
        assertTrue(groups.contains(group2));
    }

    @Test
    public void givenUser1_getModeratorGroups_returnsOnlyModeratorGroups() {
        List<Group> groups = user1.getModeratorGroups();
        assertEquals(1, groups.size());

        assertTrue(groups.contains(group1));
    }

    private void saveRelation(User user, Group group, UserGroupRole role) {
        UserGroupRelation relation = new UserGroupRelation(user.getId(), group.getId(), role);

        session.save(relation);
        session.flush();
        session.refresh(user);
        session.refresh(group);
    }

}

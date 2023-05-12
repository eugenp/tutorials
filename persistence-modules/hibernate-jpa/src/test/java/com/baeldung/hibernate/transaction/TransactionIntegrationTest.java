package com.baeldung.hibernate.transaction;

import com.baeldung.hibernate.HibernateUtil;
import com.baeldung.hibernate.pojo.Post;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Properties;

import static org.junit.Assert.assertEquals;

public class TransactionIntegrationTest {

    private static PostService postService;
    private static Session session;
    private static Logger logger = LoggerFactory.getLogger(TransactionIntegrationTest.class);

    @BeforeClass
    public static void init() throws IOException {
        Properties properties = new Properties();
        properties.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        properties.setProperty("hibernate.connection.url", "jdbc:h2:mem:mydb1;DB_CLOSE_DELAY=-1");
        properties.setProperty("hibernate.connection.username", "sa");
        properties.setProperty("hibernate.show_sql", "true");
        properties.setProperty("jdbc.password", "");
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        properties.setProperty("hibernate.hbm2ddl.auto", "create-drop");
        SessionFactory sessionFactory = HibernateUtil.getSessionFactoryByProperties(properties);
        session = sessionFactory.openSession();
        postService = new PostService(session);
    }

    @Test
    public void givenTitleAndBody_whenRepositoryUpdatePost_thenUpdatePost() {

        Post post = new Post("This is a title", "This is a sample post");
        session.persist(post);

        String title = "[UPDATE] Java HowTos";
        String body = "This is an updated posts on Java how-tos";
        postService.updatePost(title, body, post.getId());

        session.refresh(post);

        assertEquals(post.getTitle(), title);
        assertEquals(post.getBody(), body);
    }


}

/*
 * (c) Центр ИТ, 2016. Все права защищены.
 */
package com.baeldung.querydsl.intro;

import com.baeldung.querydsl.intro.entities.BlogPost;
import com.baeldung.querydsl.intro.entities.QBlogPost;
import com.baeldung.querydsl.intro.entities.QUser;
import com.baeldung.querydsl.intro.entities.User;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.junit.*;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import java.util.List;

import static org.junit.Assert.*;

public class QueryDSLIntegrationTest {

    private static EntityManagerFactory emf;

    private EntityManager em;

    private JPAQueryFactory queryFactory;

    @BeforeClass
    public static void populateDatabase() {
        emf = Persistence.createEntityManagerFactory("com.baeldung.querydsl.intro");
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        User user1 = new User();
        user1.setLogin("David");
        em.persist(user1);

        User user2 = new User();
        user2.setLogin("Ash");
        em.persist(user2);

        User user3 = new User();
        user3.setLogin("Call");
        em.persist(user3);

        User user4 = new User();
        user4.setLogin("Bishop");
        em.persist(user4);

        BlogPost blogPost1 = new BlogPost();
        blogPost1.setTitle("Hello World!");
        blogPost1.setUser(user1);
        em.persist(blogPost1);

        BlogPost blogPost2 = new BlogPost();
        blogPost2.setTitle("My Second Post");
        blogPost2.setUser(user1);
        em.persist(blogPost2);

        BlogPost blogPost3 = new BlogPost();
        blogPost3.setTitle("Hello World!");
        blogPost3.setUser(user3);
        em.persist(blogPost3);

        em.getTransaction().commit();

        em.close();

    }

    @Before
    public void setUp() {
        em = emf.createEntityManager();
        em.getTransaction().begin();
        queryFactory = new JPAQueryFactory(em);
    }

    @Test
    public void whenFindByLogin_thenShouldReturnUser() {

        QUser user = QUser.user;
        User aUser = queryFactory.selectFrom(user)
                .where(user.login.eq("David"))
                .fetchOne();

        assertNotNull(aUser);
        assertEquals(aUser.getLogin(), "David");

    }

    @Test
    public void whenUsingOrderBy_thenResultsShouldBeOrdered() {

        QUser user = QUser.user;
        List<User> users = queryFactory.selectFrom(user)
                .orderBy(user.login.asc())
                .fetch();

        assertEquals(users.size(), 4);
        assertEquals(users.get(0).getLogin(), "Ash");
        assertEquals(users.get(1).getLogin(), "Bishop");
        assertEquals(users.get(2).getLogin(), "Call");
        assertEquals(users.get(3).getLogin(), "David");

    }

    @Test
    public void whenGroupingByTitle_thenReturnsTuples() {

        QBlogPost blogPost = QBlogPost.blogPost;

        NumberPath<Long> count = Expressions.numberPath(Long.class, "c");

        List<Tuple> userTitleCounts = queryFactory.select(blogPost.title, blogPost.id.count().as(count))
                .from(blogPost)
                .groupBy(blogPost.title)
                .orderBy(count.desc())
                .fetch();

        assertEquals("Hello World!", userTitleCounts.get(0).get(blogPost.title));
        assertEquals(new Long(2), userTitleCounts.get(0).get(count));

        assertEquals("My Second Post", userTitleCounts.get(1).get(blogPost.title));
        assertEquals(new Long(1), userTitleCounts.get(1).get(count));

    }

    @Test
    public void whenJoiningWithCondition_thenResultCountShouldMatch() {

        QUser user = QUser.user;
        QBlogPost blogPost = QBlogPost.blogPost;

        List<User> users = queryFactory.selectFrom(user)
                .innerJoin(user.blogPosts, blogPost)
                .on(blogPost.title.eq("Hello World!"))
                .fetch();

        assertEquals(2, users.size());
    }

    @Test
    public void whenRefiningWithSubquery_thenResultCountShouldMatch() {

        QUser user = QUser.user;
        QBlogPost blogPost = QBlogPost.blogPost;

        List<User> users = queryFactory.selectFrom(user)
                .where(user.id.in(
                        JPAExpressions.select(blogPost.user.id)
                                .from(blogPost)
                                .where(blogPost.title.eq("Hello World!"))))
                .fetch();

        assertEquals(2, users.size());
    }

    @Test
    public void whenUpdating_thenTheRecordShouldChange() {

        QUser user = QUser.user;

        queryFactory.update(user)
                .where(user.login.eq("Ash"))
                .set(user.login, "Ash2")
                .set(user.disabled, true)
                .execute();

        em.getTransaction().commit();

        em.getTransaction().begin();

        assertEquals(Boolean.TRUE,
                queryFactory.select(user.disabled)
                        .from(user)
                        .where(user.login.eq("Ash2"))
                        .fetchOne());

    }

    @Test
    public void whenDeleting_thenTheRecordShouldBeAbsent() {

        QUser user = QUser.user;

        queryFactory.delete(user)
                .where(user.login.eq("Bishop"))
                .execute();

        em.getTransaction().commit();

        em.getTransaction().begin();

        assertNull(queryFactory.selectFrom(user)
                .where(user.login.eq("Bishop"))
                .fetchOne());

    }

    @After
    public void tearDown() {
        em.getTransaction().commit();
        em.close();
    }

    @AfterClass
    public static void afterClass() {
        emf.close();
    }

}

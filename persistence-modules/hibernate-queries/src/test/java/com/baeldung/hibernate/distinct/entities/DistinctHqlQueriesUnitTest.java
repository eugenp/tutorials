package com.baeldung.hibernate.distinct.entities;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.annotations.QueryHints;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.baeldung.hibernate.HibernateUtil;

public class DistinctHqlQueriesUnitTest {

    private Session session;

    @Before
    public void setUp() throws IOException {
        this.session = HibernateUtil.getSessionFactory()
            .openSession();
        saveDummyData();
    }

    private void saveDummyData() {
        Transaction trx = session.beginTransaction();
        session.createQuery("delete from Post")
            .executeUpdate();

        Post post = new Post();
        post.setTitle("Distinct Queries in HQL");
        post.setComments(Arrays.asList(new Comment("Great article!"), new Comment("Nice one :)"), new Comment("Keep up the good work!")));
        session.persist(post);

        trx.commit();
    }

    @After
    public void tearDown() {
        session.close();
    }

    @Test
    public void whenExecutingSelectQuery_thereWillBeDuplicates() {
        String hql = "SELECT p FROM Post p LEFT JOIN FETCH p.comments";
        List<Post> posts = session.createQuery(hql, Post.class)
            .getResultList();

        assertThat(posts).hasSize(3);
    }

    @Test
    public void whenExecutingSelectDistinctQuery_thereShouldBeNoDuplicates() {
        String hql = "SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments";
        List<Post> posts = session.createQuery(hql, Post.class)
            .getResultList();

        assertThat(posts).hasSize(1)
            .allMatch(post -> post.getTitle()
                .equals("Distinct Queries in HQL") && post.getComments()
                .size() == 3);
    }

    @Test
    public void whenExecutingSelectDistinctQueryWithHint_thereShouldBeNoDuplicates() {
        String hql = "SELECT DISTINCT p FROM Post p LEFT JOIN FETCH p.comments";
        List<Post> posts = session.createQuery(hql, Post.class)
            .setHint(QueryHints.PASS_DISTINCT_THROUGH, false)
            .getResultList();

        assertThat(posts).hasSize(1)
            .allMatch(post -> post.getTitle()
                .equals("Distinct Queries in HQL") && post.getComments()
                .size() == 3);
    }
}
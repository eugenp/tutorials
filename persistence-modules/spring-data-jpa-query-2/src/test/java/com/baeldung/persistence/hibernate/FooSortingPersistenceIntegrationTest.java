package com.baeldung.persistence.hibernate;

import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.NullPrecedence;
import org.hibernate.query.Order;
import org.hibernate.query.Query;
import org.hibernate.query.SortDirection;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.persistence.model.Bar;
import com.baeldung.persistence.model.Foo;
import com.baeldung.spring.config.PersistenceTestConfig;

import jakarta.persistence.criteria.CriteriaQuery;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceTestConfig.class }, loader = AnnotationConfigContextLoader.class)
@SuppressWarnings("unchecked")
public class FooSortingPersistenceIntegrationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(FooSortingPersistenceIntegrationTest.class);

    @Autowired
    private SessionFactory sessionFactory;

    private Session session;

    @Before
    public void before() {
        session = sessionFactory.openSession();

        session.beginTransaction();

        final FooFixtures fooData = new FooFixtures(sessionFactory);
        fooData.createBars();
    }

    @After
    public void after() {
        session.getTransaction().commit();
        session.close();
    }

    @Test
    public final void whenHQlSortingByOneAttribute_thenPrintSortedResults() {
        final String hql = "FROM Foo f ORDER BY f.name";
        final Query query = session.createQuery(hql);
        final List<Foo> fooList = query.list();
        for (final Foo foo : fooList) {
            LOGGER.debug("Name: {} , Id: {}", foo.getName(), foo.getId());
        }
    }

    @Test
    public final void whenHQlSortingByStringNullLast_thenLastNull() {
        final String hql = "FROM Foo f ORDER BY f.name NULLS LAST";
        final Query query = session.createQuery(hql);
        final List<Foo> fooList = query.list();

        assertNull(fooList.get(fooList.toArray().length - 1).getName());
        for (final Foo foo : fooList) {
            LOGGER.debug("Name: {}, Id: {}", foo.getName(), foo.getId());
        }
    }

    @Test
    public final void whenSortingByStringNullsFirst_thenReturnNullsFirst() {
        final String hql = "FROM Foo f ORDER BY f.name NULLS FIRST";
        final Query query = session.createQuery(hql);
        final List<Foo> fooList = query.list();
        assertNull(fooList.get(0).getName());
        for (final Foo foo : fooList) {
            LOGGER.debug("Name: {}", foo.getName());

        }
    }

    @Test
    public final void whenHQlSortingByOneAttribute_andOrderDirection_thenPrintSortedResults() {
        final String hql = "FROM Foo f ORDER BY f.name ASC";
        Query<Foo> query = session.createQuery(hql, Foo.class);
        final List<Foo> fooList = query.list();
        for (final Foo foo : fooList) {
            LOGGER.debug("Name: {}, Id: {}", foo.getName(), foo.getId());
        }
    }

    @Test
    public final void whenHQlSortingByMultipleAttributes_thenSortedResults() {
        final String hql = "FROM Foo f ORDER BY f.name, f.id";
        Query<Foo> query = session.createQuery(hql, Foo.class);
        final List<Foo> fooList = query.list();
        for (final Foo foo : fooList) {
            LOGGER.debug("Name: {}, Id: {}", foo.getName(), foo.getId());
        }
    }

    @Test
    public final void whenHQlSortingByMultipleAttributes_andOrderDirection_thenPrintSortedResults() {
        final String hql = "FROM Foo f ORDER BY f.name DESC, f.id ASC";
        Query<Foo> query = session.createQuery(hql, Foo.class);
        final List<Foo> fooList = query.list();
        for (final Foo foo : fooList) {
            LOGGER.debug("Name: {}, Id: {}", foo.getName(), foo.getId());
        }
    }

    @Test
    public final void whenHQLCriteriaSortingByOneAttr_thenPrintSortedResults() {
        CriteriaQuery<Foo> selectQuery = session.getCriteriaBuilder().createQuery(Foo.class);
        selectQuery.from(Foo.class);
        Query<Foo> query = session.createQuery(selectQuery);

        query.setOrder(Collections.singletonList(Order.asc(Foo.class,"id")));
        final List<Foo> fooList = query.list();
        for (final Foo foo : fooList) {
            LOGGER.debug("Id: {}, FirstName: {}", foo.getId(), foo.getName());
        }
    }

    @Test
    public final void whenHQLCriteriaSortingByMultipAttr_thenSortedResults() {

        CriteriaQuery<Foo> selectQuery = session.getCriteriaBuilder().createQuery(Foo.class);
        selectQuery.from(Foo.class);
        Query<Foo> query = session.createQuery(selectQuery);

        List<Order<? super Foo>> orderBy = new ArrayList<>(2);
        orderBy.add(Order.asc(Foo.class,"name"));
        orderBy.add(Order.asc(Foo.class,"id"));
        query.setOrder(orderBy);
        final List<Foo> fooList = query.list();
        for (final Foo foo : fooList) {
            LOGGER.debug("Id: {}, FirstName: {}", foo.getId(), foo.getName());
        }
    }

    @Test
    public final void whenCriteriaSortingStringNullsLastAsc_thenNullsLast() {
        CriteriaQuery<Foo> selectQuery = session.getCriteriaBuilder().createQuery(Foo.class);
        selectQuery.from(Foo.class);
        Query<Foo> query = session.createQuery(selectQuery);

        List<Order<? super Foo>> orderBy = new ArrayList<>(2);
        orderBy.add(Order.by(Foo.class,"name", SortDirection.ASCENDING, NullPrecedence.LAST));
        query.setOrder(orderBy);

        final List<Foo> fooList = query.list();
        assertNull(fooList.get(fooList.toArray().length - 1).getName());
        for (final Foo foo : fooList) {
            LOGGER.debug("Id: {}, FirstName: {}", foo.getId(), foo.getName());
        }
    }

    @Test
    public final void whenCriteriaSortingStringNullsFirstDesc_thenNullsFirst() {
        CriteriaQuery<Foo> selectQuery = session.getCriteriaBuilder().createQuery(Foo.class);
        selectQuery.from(Foo.class);
        Query<Foo> query = session.createQuery(selectQuery);

        List<Order<? super Foo>> orderBy = new ArrayList<>(2);
        orderBy.add(Order.by(Foo.class,"name", SortDirection.ASCENDING, NullPrecedence.FIRST));
        query.setOrder(orderBy);

        final List<Foo> fooList = query.list();
        assertNull(fooList.get(0).getName());
        for (final Foo foo : fooList) {
            LOGGER.debug("Id: {}, FirstName: {}", foo.getId(), foo.getName());
        }
    }

    @Test
    public final void whenSortingBars_thenBarsWithSortedFoos() {
        final String hql = "FROM Bar b ORDER BY b.id";
        final Query<Bar> query = session.createQuery(hql, Bar.class);
        final List<Bar> barList = query.list();
        for (final Bar bar : barList) {
            final Set<Foo> fooSet = bar.getFooSet();
            LOGGER.debug("Bar Id:{}", bar.getId());
            for (final Foo foo : fooSet) {
                LOGGER.debug("FooName:{}", foo.getName());
            }
        }
    }

}

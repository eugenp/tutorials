package com.baeldung.persistence.hibernate;

import static org.junit.Assert.assertNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Root;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.baeldung.persistence.model.Bar;
import com.baeldung.persistence.model.Foo;
import com.baeldung.spring.PersistenceConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
@SuppressWarnings("unchecked")
public class FooSortingPersistenceIntegrationTest {

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
        final List<Foo> fooList = session.createQuery(hql).getResultList();
        for (final Foo foo : fooList) {
            System.out.println("Name: " + foo.getName() + ", Id: " + foo.getId());
        }
    }

    @Test
    public final void whenHQlSortingByStringNullLast_thenLastNull() {
        final String hql = "FROM Foo f ORDER BY f.name NULLS LAST";
        final List<Foo> fooList = session.createQuery(hql).getResultList();

        assertNull(fooList.get(fooList.toArray().length - 1).getName());
        for (final Foo foo : fooList) {
            System.out.println("Name: " + foo.getName() + ", Id: " + foo.getId());
        }
    }

    @Test
    public final void whenSortingByStringNullsFirst_thenReturnNullsFirst() {
        final String hql = "FROM Foo f ORDER BY f.name NULLS FIRST";
        final List<Foo> fooList = session.createQuery(hql).getResultList();
        assertNull(fooList.get(0).getName());
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName());

        }
    }

    @Test
    public final void whenHQlSortingByOneAttribute_andOrderDirection_thenPrintSortedResults() {
        final String hql = "FROM Foo f ORDER BY f.name ASC";
        final List<Foo> fooList = session.createQuery(hql).getResultList();
        for (final Foo foo : fooList) {
            System.out.println("Name: " + foo.getName() + ", Id: " + foo.getId());
        }
    }

    @Test
    public final void whenHQlSortingByMultipleAttributes_thenSortedResults() {
        final String hql = "FROM Foo f ORDER BY f.name, f.id";
        final List<Foo> fooList = session.createQuery(hql).getResultList();
        for (final Foo foo : fooList) {
            System.out.println("Name: " + foo.getName() + ", Id: " + foo.getId());
        }
    }

    @Test
    public final void whenHQlSortingByMultipleAttributes_andOrderDirection_thenPrintSortedResults() {
        final String hql = "FROM Foo f ORDER BY f.name DESC, f.id ASC";
        final List<Foo> fooList = session.createQuery(hql).getResultList();
        for (final Foo foo : fooList) {
            System.out.println("Name: " + foo.getName() + ", Id: " + foo.getId());
        }
    }

    @Test
    public final void whenHQLCriteriaSortingByOneAttr_thenPrintSortedResults() {
        List<Order> listOrders = new ArrayList<Order>();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Foo> criteriaItem = builder.createQuery(Foo.class);
        Root<Foo> rootItem = criteriaItem.from(Foo.class);
        listOrders.add(builder.asc(rootItem.get("id")));
        criteriaItem.orderBy(listOrders.toArray(new Order[] {}));
        final List<Foo> fooList = session.createQuery(criteriaItem).getResultList();
        for (final Foo foo : fooList) {
            System.out.println("Id: " + foo.getId() + ", FirstName: " + foo.getName());
        }
    }

    @Test
    public final void whenHQLCriteriaSortingByMultipAttr_thenSortedResults() {
        List<Order> listOrders = new ArrayList<Order>();
        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Foo> criteriaItem = builder.createQuery(Foo.class);
        Root<Foo> rootItem = criteriaItem.from(Foo.class);
        listOrders.add(builder.asc(rootItem.get("name")));
        listOrders.add(builder.asc(rootItem.get("id")));
        criteriaItem.orderBy(listOrders.toArray(new Order[] {}));
        final List<Foo> fooList = session.createQuery(criteriaItem).getResultList();
        for (final Foo foo : fooList) {
            System.out.println("Id: " + foo.getId() + ", FirstName: " + foo.getName());
        }
    }

    /*@Test
    public final void whenCriteriaSortingStringNullsLastAsc_thenNullsLast() {
        final Criteria criteria = session.createCriteria(Foo.class, "FOO");
        criteria.addOrder(Order.asc("name").nulls(NullPrecedence.LAST));
        final List<Foo> fooList = criteria.list();
        assertNull(fooList.get(fooList.toArray().length - 1).getName());
        for (final Foo foo : fooList) {
            System.out.println("Id: " + foo.getId() + ", FirstName: " + foo.getName());
        }
    }
    
    @Test
    public final void whenCriteriaSortingStringNullsFirstDesc_thenNullsFirst() {
        final Criteria criteria = session.createCriteria(Foo.class, "FOO");
        criteria.addOrder(Order.desc("name").nulls(NullPrecedence.FIRST));
        final List<Foo> fooList = criteria.list();
        assertNull(fooList.get(0).getName());
        for (final Foo foo : fooList) {
            System.out.println("Id: " + foo.getId() + ", FirstName: " + foo.getName());
        }
    }*/

    @Test
    public final void whenSortingBars_thenBarsWithSortedFoos() {
        final String hql = "FROM Bar b ORDER BY b.id";
        final List<Bar> barList = session.createQuery(hql).getResultList();
        for (final Bar bar : barList) {
            final Set<Foo> fooSet = bar.getFooSet();
            System.out.println("Bar Id:" + bar.getId());
            for (final Foo foo : fooSet) {
                System.out.println("FooName:" + foo.getName());
            }
        }
    }

}

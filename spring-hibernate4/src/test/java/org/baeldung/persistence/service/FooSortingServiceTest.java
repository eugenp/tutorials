package org.baeldung.persistence.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.baeldung.persistence.model.Bar;
import org.baeldung.persistence.model.Foo;
import org.baeldung.spring.PersistenceConfig;
import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.common.collect.Lists;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
public class FooSortingServiceTest {

    // tests

    @Test
    public final void whenHQlSortingByOneAttribute_thenPrintSortedResults() {
        Session sess = null;
        List<Foo> fooList = Lists.newArrayList();

        try {
            final SessionFactory sf = new Configuration().configure().buildSessionFactory();
            sess = sf.openSession();
            final String hql = "FROM Foo f ORDER BY f.name";
            final Query query = sess.createQuery(hql);
            fooList = query.list();
            for (final Foo foo : fooList) {
                System.out.println("Name: " + foo.getName() + ", Id: " + foo.getId());
            }
            final Transaction tr = sess.beginTransaction();
            tr.commit();
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }

    }

    @Test
    public final void whenHQlSortingByOneAttribute_andOrderDirection_thenPrintSortedResults() {
        Session sess = null;
        List<Foo> fooList = Lists.newArrayList();

        try {
            final SessionFactory sf = new Configuration().configure().buildSessionFactory();
            sess = sf.openSession();
            final String hql = "FROM Foo f ORDER BY f.name ASC";
            final Query query = sess.createQuery(hql);
            fooList = query.list();
            for (final Foo foo : fooList) {
                System.out.println("Name: " + foo.getName() + ", Id: " + foo.getId());
            }
            final Transaction tr = sess.beginTransaction();
            tr.commit();
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }

    }

    @Test
    public final void whenHQlSortingByMultipleAttributes_thenSortedResults() {
        Session sess = null;
        List<Foo> fooList = Lists.newArrayList();

        try {
            final SessionFactory sf = new Configuration().configure().buildSessionFactory();
            sess = sf.openSession();
            final String hql = "FROM Foo f ORDER BY f.name, f.id";
            final Query query = sess.createQuery(hql);
            fooList = query.list();
            for (final Foo foo : fooList) {
                System.out.println("Name: " + foo.getName() + ", Id: " + foo.getId());
            }
            final Transaction tr = sess.beginTransaction();
            tr.commit();
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }

    }

    @Test
    public final void whenHQlSortingByMultipleAttributes_andOrderDirection_thenPrintSortedOrderedResults() {
        Session sess = null;
        List<Foo> fooList = Lists.newArrayList();

        try {
            final SessionFactory sf = new Configuration().configure().buildSessionFactory();
            sess = sf.openSession();
            final String hql = "FROM Foo f ORDER BY f.name DESC, f.id ASC";
            final Query query = sess.createQuery(hql);
            fooList = query.list();
            for (final Foo foo : fooList) {
                System.out.println("Name: " + foo.getName() + ", Id: " + foo.getId());
            }
            final Transaction tr = sess.beginTransaction();
            tr.commit();
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }

    }

    @Test
    public final void whenCriteriaSortingByOneAttr_thenPrintSortedResults() {
        Session sess = null;
        final SessionFactory sf = new Configuration().configure().buildSessionFactory();
        sess = sf.openSession();
        List<Foo> fooList = Lists.newArrayList();
        try {
            sess.beginTransaction();
            final Criteria criteria = sess.createCriteria(Foo.class, "FOO");
            criteria.addOrder(Order.asc("id"));
            fooList = criteria.list();
            assertEquals(1, fooList.get(0).getId());
            assertEquals(100, fooList.get(fooList.toArray().length - 1).getId());
            for (final Foo foo : fooList) {
                System.out.println("Id: " + foo.getId() + ", FirstName: " + foo.getName());
            }

            sess.getTransaction().commit();
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    @Test
    public final void whenCriteriaSortingByMultipAttr_thenSortedResults() {
        Session sess = null;
        final SessionFactory sf = new Configuration().configure().buildSessionFactory();
        sess = sf.openSession();
        List<Foo> fooList = Lists.newArrayList();
        try {
            sess.beginTransaction();
            final Criteria criteria = sess.createCriteria(Foo.class, "FOO");
            criteria.addOrder(Order.asc("name"));
            criteria.addOrder(Order.asc("id"));
            fooList = criteria.list();
            for (final Foo foo : fooList) {
                System.out.println("Id: " + foo.getId() + ", FirstName: " + foo.getName());
            }

            sess.getTransaction().commit();
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    @Test
    public final void whenCriteriaSortingStringNullsLastAsc_thenNullsLast() {
        Session sess = null;
        final SessionFactory sf = new Configuration().configure().buildSessionFactory();
        sess = sf.openSession();
        List<Foo> fooList = Lists.newArrayList();
        try {
            sess.beginTransaction();
            final Criteria criteria = sess.createCriteria(Foo.class, "FOO");
            criteria.addOrder(Order.asc("name").nulls(NullPrecedence.LAST));
            fooList = criteria.list();
            assertNull(fooList.get(fooList.toArray().length - 1).getName());
            for (final Foo foo : fooList) {
                System.out.println("Id: " + foo.getId() + ", FirstName: " + foo.getName());
            }
            sess.getTransaction().commit();
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    @Test
    public final void whenCriteriaSortingStringNullsFirstDesc_thenNullsFirst() {
        Session sess = null;
        final SessionFactory sf = new Configuration().configure().buildSessionFactory();
        sess = sf.openSession();
        List<Foo> fooList = Lists.newArrayList();
        try {
            sess.beginTransaction();
            final Criteria criteria = sess.createCriteria(Foo.class, "FOO");
            criteria.addOrder(Order.desc("name").nulls(NullPrecedence.FIRST));
            fooList = criteria.list();
            assertNull(fooList.get(0).getName());
            for (final Foo foo : fooList) {
                System.out.println("Id: " + foo.getId() + ", FirstName: " + foo.getName());
            }
            sess.getTransaction().commit();
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }

    @Test
    public final void whenHQlSortingByStringNullLast_thenLastNull() {
        Session sess = null;
        List<Foo> fooList = Lists.newArrayList();

        try {
            final SessionFactory sf = new Configuration().configure().buildSessionFactory();
            sess = sf.openSession();
            final String hql = "FROM Foo f ORDER BY f.name NULLS LAST";

            final Query query = sess.createQuery(hql);
            fooList = query.list();
            assertNull(fooList.get(fooList.toArray().length - 1).getName());
            for (final Foo foo : fooList) {
                System.out.println("Name: " + foo.getName() + ", Id: " + foo.getId());
            }
            final Transaction tr = sess.beginTransaction();
            tr.commit();
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }

    }

    @Test
    public final void whenSortingBars_thenBarsWithSortedFoos() {
        Session sess = null;
        final Set<Foo> fooList = new TreeSet();
        List<Bar> barList = Lists.newArrayList();

        try {
            final SessionFactory sf = new Configuration().configure().buildSessionFactory();
            sess = sf.openSession();
            final String hql = "FROM Bar b ORDER BY b.id";
            final Query query = sess.createQuery(hql);
            barList = query.list();

            for (final Bar bar : barList) {
                System.out.println("Bar Id:" + bar.getId());
                for (final Foo foo : bar.getFooList()) {
                    System.out.println("FooName:" + foo.getName());
                }
            }
            final Transaction tr = sess.beginTransaction();
            tr.commit();
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }

    }

    // @Test
    // public final void whenSortingPrimitiveNulls_thenException() {
    // Session sess = null;
    // List<Foo> fooList = new ArrayList();
    // final List<Bar> barList = new ArrayList();
    //
    // try {
    // final SessionFactory sf = new Configuration().configure().buildSessionFactory();
    // sess = sf.openSession();
    // final String hql = "FROM Foo f ORDER BY f.idx";
    // final Query query = sess.createQuery(hql);
    // boolean thrown = false;
    // try {
    // fooList = criteria.list();
    // } catch (final org.hibernate.PropertyAccessException e) {
    // thrown = true;
    // }
    // assertTrue(thrown);
    //
    // final Transaction tr = sess.beginTransaction();
    // tr.commit();
    // } catch (final Exception ex) {
    // ex.printStackTrace();
    // } finally {
    // if (sess != null) {
    // sess.close();
    // }
    // }
    // }

    @Test
    public final void whenSortingStringNullsLast_thenReturnNullsLast() {
        Session sess = null;
        List<Foo> fooList = Lists.newArrayList();
        final List<Bar> barList = Lists.newArrayList();

        try {
            final SessionFactory sf = new Configuration().configure().buildSessionFactory();
            sess = sf.openSession();
            final String hql = "FROM Foo f ORDER BY f.name NULLS LAST";
            final Query query = sess.createQuery(hql);
            fooList = query.list();
            assertNull(fooList.get(fooList.toArray().length - 1).getName());
            for (final Foo foo : fooList) {
                System.out.println("FooIDX:" + foo.getName());

            }

            final Transaction tr = sess.beginTransaction();
            tr.commit();
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }

    }

    @Test
    public final void whenNullPrimitiveValueCriteriaSortingByMultipAttr_thenException() {
        Session sess = null;
        final SessionFactory sf = new Configuration().configure().buildSessionFactory();
        sess = sf.openSession();
        List<Foo> fooList = Lists.newArrayList();
        try {
            sess.beginTransaction();
            final Criteria criteria = sess.createCriteria(Foo.class, "FOO");
            criteria.addOrder(Order.desc("name").nulls(NullPrecedence.FIRST));
            criteria.addOrder(Order.asc("idx"));
            boolean thrown = false;
            try {
                fooList = criteria.list();
            } catch (final org.hibernate.PropertyAccessException e) {
                thrown = true;
            }
            assertTrue(thrown);

            sess.getTransaction().commit();
        } catch (final Exception ex) {
            ex.printStackTrace();
        } finally {
            if (sess != null) {
                sess.close();
            }
        }
    }
}

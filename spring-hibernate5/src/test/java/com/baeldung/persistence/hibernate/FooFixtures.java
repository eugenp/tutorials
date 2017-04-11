package com.baeldung.persistence.hibernate;

import java.util.List;

import com.baeldung.persistence.model.Foo;
import com.baeldung.persistence.model.Bar;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.common.collect.Lists;

public class FooFixtures {
    private SessionFactory sessionFactory;

    public FooFixtures(final SessionFactory sessionFactory) {
        super();

        this.sessionFactory = sessionFactory;
    }

    // API

    public void createBars() {
        Session session = null;
        Transaction tx = null;
        session = sessionFactory.openSession();
        tx = session.getTransaction();
        try {
            tx.begin();
            for (int i = 156; i < 160; i++) {
                final Bar bar = new Bar();
                bar.setName("Bar_" + i);
                final Foo foo = new Foo("Foo_" + (i + 120));
                foo.setBar(bar);
                session.save(foo);
                final Foo foo2 = new Foo(null);
                if (i % 2 == 0)
                    foo2.setName("LuckyFoo" + (i + 120));
                foo2.setBar(bar);
                session.save(foo2);
                bar.getFooSet().add(foo);
                bar.getFooSet().add(foo2);
                session.merge(bar);
            }
            tx.commit();
            session.flush();
        } catch (final HibernateException he) {
            if (tx != null)
                tx.rollback();
            System.out.println("Not able to open session");
            he.printStackTrace();
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }

    }

    public void createFoos() {
        Session session = null;
        Transaction tx = null;
        session = sessionFactory.openSession();
        tx = session.getTransaction();
        final List<Foo> fooList = Lists.newArrayList();
        for (int i = 35; i < 46; i++) {

            final Foo foo = new Foo();
            foo.setName("Foo_" + (i + 120));
            final Bar bar = new Bar("bar_" + i);
            bar.getFooSet().add(foo);
            foo.setBar(bar);
            fooList.add(foo);

        }
        try {
            tx.begin();
            for (final Foo foo : fooList) {

                session.save(foo.getBar());
                session.save(foo);
            }
            tx.commit();
            session.flush();
        } catch (final HibernateException he) {
            if (tx != null)
                tx.rollback();
            System.out.println("Not able to open session");
            he.printStackTrace();
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (session != null)
                session.close();
        }
    }

}

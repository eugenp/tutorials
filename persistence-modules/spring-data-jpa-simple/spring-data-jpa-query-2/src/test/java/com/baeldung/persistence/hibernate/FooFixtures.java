package com.baeldung.persistence.hibernate;

import java.util.List;

import com.baeldung.persistence.model.Foo;
import com.baeldung.persistence.model.Bar;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FooFixtures {

    private static final Logger LOGGER = LoggerFactory.getLogger(FooFixtures.class);

    private final SessionFactory sessionFactory;

    public FooFixtures(final SessionFactory sessionFactory) {
        super();

        this.sessionFactory = sessionFactory;
    }

    // API

    public void createBars() {
        Session session;
        Transaction tx;
        session = sessionFactory.openSession();
        tx = session.getTransaction();
        try {
            tx.begin();
            for (int i = 156; i < 160; i++) {
                final Bar bar = new Bar();
                bar.setName("Bar_" + i);
                final Foo foo = new Foo("Foo_" + (i + 120));
                foo.setBar(bar);
                session.persist(foo);
                final Foo foo2 = new Foo(null);
                if (i % 2 == 0) {
                    foo2.setName("LuckyFoo" + (i + 120));
                }
                foo2.setBar(bar);
                session.persist(foo2);
                bar.getFooSet().add(foo);
                bar.getFooSet().add(foo2);
                session.merge(bar);
            }
            tx.commit();
            session.flush();
        } catch (final HibernateException he) {
            tx.rollback();
            LOGGER.error("Not able to open session", he);
        } catch (final Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } finally {
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
                session.persist(foo.getBar());
                session.persist(foo);
            }
            tx.commit();
            session.flush();
        } catch (final HibernateException he) {
            tx.rollback();
            LOGGER.error("Not able to open session", he);
        } catch (final Exception e) {
            LOGGER.error(e.getLocalizedMessage(), e);
        } finally {
            session.close();
        }
    }

}

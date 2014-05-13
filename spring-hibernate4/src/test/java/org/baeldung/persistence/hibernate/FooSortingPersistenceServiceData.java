package org.baeldung.persistence.hibernate;

import java.util.List;

import org.baeldung.persistence.model.Bar;
import org.baeldung.persistence.model.Foo;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.google.common.collect.Lists;

public class FooSortingPersistenceServiceData {
    private static ServiceRegistry serviceRegistry;
    private static SessionFactory sessionFactory;
    private static Configuration configuration;
    private static StandardServiceRegistryBuilder builder;

    public FooSortingPersistenceServiceData() {
        super();
    }

    public void createBars() {

        configWork();
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

        configWork();
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

    public void configWork() {
        configuration = new Configuration();
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty(AvailableSettings.DRIVER, "com.mysql.jdbc.Driver");
        configuration.setProperty(AvailableSettings.URL, "jdbc:mysql://localhost:3306/HIBERTEST2_TEST");
        configuration.setProperty(AvailableSettings.USER, "root");
        configuration.setProperty(AvailableSettings.PASS, "");
        builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.addPackage("com.cc.example.hibernate").addAnnotatedClass(Foo.class).addAnnotatedClass(Bar.class).configure().buildSessionFactory(builder.build());
    }
}

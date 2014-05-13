package org.baeldung.persistence.hibernate;

import static org.junit.Assert.assertNull;

import java.util.List;
import java.util.Set;

import javax.imageio.spi.ServiceRegistry;

import org.baeldung.persistence.model.Bar;
import org.baeldung.persistence.model.Foo;
import org.baeldung.spring.PersistenceConfig;
import org.hibernate.Criteria;
import org.hibernate.NullPrecedence;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.AvailableSettings;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Order;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { PersistenceConfig.class }, loader = AnnotationConfigContextLoader.class)
@SuppressWarnings("unchecked")
public class FooSortingPersistenceServiceTest {
    private SessionFactory sf;
    private Session sess;
    private static ServiceRegistry serviceRegistry;
    private static Configuration configuration;
    private static StandardServiceRegistryBuilder builder;

    @Before
    public void before() {

        final FooSortingPersistenceServiceData fooData = new FooSortingPersistenceServiceData();
        fooData.createBars();
        configuration = new Configuration();
        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty("dialect", "org.hibernate.dialect.MySQLDialect");
        configuration.setProperty(AvailableSettings.DRIVER, "com.mysql.jdbc.Driver");
        configuration.setProperty(AvailableSettings.URL, "jdbc:mysql://localhost:3306/HIBERTEST2_TEST");
        configuration.setProperty(AvailableSettings.USER, "root");
        configuration.setProperty(AvailableSettings.PASS, "");
        configuration.setProperty("hibernate.show_sql", "true");
        builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sf = configuration.addPackage("org.baeldung.persistence.model").addAnnotatedClass(Foo.class).addAnnotatedClass(Bar.class).configure().buildSessionFactory(builder.build());
        sess = sf.openSession();
        sess.beginTransaction();
    }

    @After
    public void after() {
        sess.getTransaction().commit();
    }

    @Test
    public final void whenHQlSortingByOneAttribute_thenPrintSortedResults() {
        final String hql = "FROM Foo f ORDER BY f.name";
        final Query query = sess.createQuery(hql);
        final List<Foo> fooList = query.list();
        for (final Foo foo : fooList) {
            System.out.println("Name: " + foo.getName() + ", Id: " + foo.getId());
        }
    }

    @Test
    public final void whenHQlSortingByStringNullLast_thenLastNull() {
        final String hql = "FROM Foo f ORDER BY f.name NULLS LAST";
        final Query query = sess.createQuery(hql);
        final List<Foo> fooList = query.list();

        assertNull(fooList.get(fooList.toArray().length - 1).getName());
        for (final Foo foo : fooList) {
            System.out.println("Name: " + foo.getName() + ", Id: " + foo.getId());
        }
    }

    @Test
    public final void whenSortingByStringNullsFirst_thenReturnNullsFirst() {
        final String hql = "FROM Foo f ORDER BY f.name NULLS FIRST";
        final Query query = sess.createQuery(hql);
        final List<Foo> fooList = query.list();
        assertNull(fooList.get(0).getName());
        for (final Foo foo : fooList) {
            System.out.println("Name:" + foo.getName());

        }
    }

    @Test
    public final void whenHQlSortingByOneAttribute_andOrderDirection_thenPrintSortedResults() {
        final String hql = "FROM Foo f ORDER BY f.name ASC";
        final Query query = sess.createQuery(hql);
        final List<Foo> fooList = query.list();
        for (final Foo foo : fooList) {
            System.out.println("Name: " + foo.getName() + ", Id: " + foo.getId());
        }
    }

    @Test
    public final void whenHQlSortingByMultipleAttributes_thenSortedResults() {
        final String hql = "FROM Foo f ORDER BY f.name, f.id";
        final Query query = sess.createQuery(hql);
        final List<Foo> fooList = query.list();
        for (final Foo foo : fooList) {
            System.out.println("Name: " + foo.getName() + ", Id: " + foo.getId());
        }
    }

    @Test
    public final void whenHQlSortingByMultipleAttributes_andOrderDirection_thenPrintSortedResults() {
        final String hql = "FROM Foo f ORDER BY f.name DESC, f.id ASC";
        final Query query = sess.createQuery(hql);
        final List<Foo> fooList = query.list();
        for (final Foo foo : fooList) {
            System.out.println("Name: " + foo.getName() + ", Id: " + foo.getId());
        }
    }

    @Test
    public final void whenHQLCriteriaSortingByOneAttr_thenPrintSortedResults() {
        final Criteria criteria = sess.createCriteria(Foo.class, "FOO");
        criteria.addOrder(Order.asc("id"));
        final List<Foo> fooList = criteria.list();
        for (final Foo foo : fooList) {
            System.out.println("Id: " + foo.getId() + ", FirstName: " + foo.getName());
        }
    }

    @Test
    public final void whenHQLCriteriaSortingByMultipAttr_thenSortedResults() {
        final Criteria criteria = sess.createCriteria(Foo.class, "FOO");
        criteria.addOrder(Order.asc("name"));
        criteria.addOrder(Order.asc("id"));
        final List<Foo> fooList = criteria.list();
        for (final Foo foo : fooList) {
            System.out.println("Id: " + foo.getId() + ", FirstName: " + foo.getName());
        }
    }

    @Test
    public final void whenCriteriaSortingStringNullsLastAsc_thenNullsLast() {
        final Criteria criteria = sess.createCriteria(Foo.class, "FOO");
        criteria.addOrder(Order.asc("name").nulls(NullPrecedence.LAST));
        final List<Foo> fooList = criteria.list();
        assertNull(fooList.get(fooList.toArray().length - 1).getName());
        for (final Foo foo : fooList) {
            System.out.println("Id: " + foo.getId() + ", FirstName: " + foo.getName());
        }
    }

    @Test
    public final void whenCriteriaSortingStringNullsFirstDesc_thenNullsFirst() {
        final Criteria criteria = sess.createCriteria(Foo.class, "FOO");
        criteria.addOrder(Order.desc("name").nulls(NullPrecedence.FIRST));
        final List<Foo> fooList = criteria.list();
        assertNull(fooList.get(0).getName());
        for (final Foo foo : fooList) {
            System.out.println("Id: " + foo.getId() + ", FirstName: " + foo.getName());
        }
    }

    @Test
    public final void whenSortingBars_thenBarsWithSortedFoos() {
        final String hql = "FROM Bar b ORDER BY b.id";
        final Query query = sess.createQuery(hql);
        final List<Bar> barList = query.list();
        for (final Bar bar : barList) {
            final Set<Foo> fooSet = bar.getFooSet();
            System.out.println("Bar Id:" + bar.getId());
            for (final Foo foo : fooSet) {
                System.out.println("FooName:" + foo.getName());
            }
        }
    }

}
